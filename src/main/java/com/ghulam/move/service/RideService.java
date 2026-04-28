package com.ghulam.move.service;

import com.ghulam.move.constant.Constants;
import com.ghulam.move.dtos.RideRequest;
import com.ghulam.move.dtos.RideResponse;
import com.ghulam.move.entity.Ride;
import com.ghulam.move.enums.RideStatus;
import com.ghulam.move.kafka.RideRequestedEvent;
import com.ghulam.move.repo.RideRepo;
import com.ghulam.move.exception.RideNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepo rideRepo;
    private final KafkaTemplate<String, RideRequestedEvent> kafkaTemplate;

    public RideResponse requestRide(RideRequest rideRequest) {
        log.info("Requesting a new ride for customer: {}", rideRequest.customerId());

        Ride ride = new Ride();
        ride.setCustomerId(rideRequest.customerId());
        ride.setPickupLongitude(rideRequest.pickupLongitude());
        ride.setPickupLatitude(rideRequest.pickupLatitude());
        ride.setPickupAddress(rideRequest.pickupAddress());
        ride.setDropLongitude(rideRequest.dropLongitude());
        ride.setDropLatitude(rideRequest.dropLatitude());
        ride.setDropAddress(rideRequest.dropAddress());
        ride.setStatus(RideStatus.RIDE_REQUESTED);
        ride.setFare(Constants.HARDCODE_RIDE_FARE);
        rideRepo.save(ride);


        RideRequestedEvent event = new RideRequestedEvent(
                ride.getId(),
                ride.getCustomerId(),
                null,
                ride.getPickupLongitude(),
                ride.getPickupLatitude(),
                ride.getPickupAddress(),
                ride.getDropLongitude(),
                ride.getDropLatitude(),
                ride.getDropAddress()
        );

        kafkaTemplate.send(Constants.RIDE_REQUESTED_TOPIC, ride.getId(), event);
        log.info("Published RideRequestedEvent for rideId: {}", ride.getId());

        ride.setStatus(RideStatus.RIDE_MATCHING);
        rideRepo.save(ride);

        return getRideResponse(ride);
    }

    private RideResponse getRideResponse(Ride ride) {
        return new RideResponse(
                ride.getId(),
                ride.getCustomerId(),
                ride.getDriverId(),
                ride.getPickupLongitude(),
                ride.getPickupLatitude(),
                ride.getPickupAddress(),
                ride.getDropLongitude(),
                ride.getDropLatitude(),
                ride.getDropAddress(),
                ride.getStatus(),
                ride.getFare(),
                ride.getCreatedTimestamp(),
                ride.getUpdatedTimestamp(),
                ride.getRideStartedTimestamp(),
                ride.getRideCompletedTimestamp()
        );
    }

    public RideResponse getRideById(String rideId) {
        log.info("Getting ride for customer: {}", rideId);

        Ride ride = rideRepo.findById(rideId).orElseThrow(
                () -> new RideNotFoundException("Ride not found with id: " + rideId)
        );

        return getRideResponse(ride);
    }
}
