package com.ghulam.move.service;

import com.ghulam.move.constant.Constants;
import com.ghulam.move.dtos.RideRequest;
import com.ghulam.move.dtos.RideResponse;
import com.ghulam.move.entity.Ride;
import com.ghulam.move.enums.RideStatus;
import com.ghulam.move.exception.RideNotFoundException;
import com.ghulam.move.kafka.event.RideRequestedEvent;
import com.ghulam.move.repo.RideRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

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

    public Set<RideResponse> getRidesByCustomerId(@Valid String customerId) {
        log.info("Getting rides for customer: {}", customerId);

        return rideRepo.findByCustomerIdOrderByCreatedTimestampDesc(customerId)
                .stream()
                .map(this::getRideResponse)
                .collect(java.util.stream.Collectors.toSet());
    }

    public RideResponse startRide(String rideId) {
        log.info("Starting ride of Id: {}", rideId);

        Ride ride = rideRepo.findById(rideId).orElseThrow(
                () -> new RideNotFoundException("Ride not found with id: " + rideId)
        );

        if (ride.getStatus() != RideStatus.RIDE_ACCEPTED) {
            throw new IllegalStateException("Ride cannot be started. status of the ride: " + ride.getStatus());
        }

        ride.setStatus(RideStatus.RIDE_STARTED);
        ride.setRideStartedTimestamp(LocalDateTime.now());
        rideRepo.save(ride);
        return getRideResponse(ride);
    }

    public RideResponse completeRide(String rideId) {
        log.info("Completing ride of Id: {}", rideId);

        Ride ride = rideRepo.findById(rideId).orElseThrow(
                () -> new RideNotFoundException("Ride not found with id: " + rideId)
        );

        if (ride.getStatus() != RideStatus.RIDE_STARTED) {
            throw new IllegalStateException("Ride cannot be completed. status of the ride: " + ride.getStatus());
        }

        ride.setStatus(RideStatus.RIDE_COMPLETED);
        ride.setRideCompletedTimestamp(LocalDateTime.now());
        rideRepo.save(ride);
        return getRideResponse(ride);
    }

    public RideResponse cancelRide(String rideId) {
        log.info("Canceling ride of Id: {}", rideId);

        Ride ride = rideRepo.findById(rideId).orElseThrow(
                () -> new RideNotFoundException("Ride not found with id: " + rideId)
        );

        if (ride.getStatus() != RideStatus.RIDE_COMPLETED) {
            throw new IllegalStateException("Ride cannot be cancelled. status of the ride: " + ride.getStatus());
        }

        ride.setStatus(RideStatus.RIDE_CANCELLED);
        rideRepo.save(ride);
        return getRideResponse(ride);
    }

    public Set<RideResponse> database() {
        log.info("Getting all rides from database");

        return rideRepo.findAll()
                .stream()
                .map(this::getRideResponse)
                .collect(java.util.stream.Collectors.toSet());
    }

    public void updateRideWithDriver(String rideId, String driverId) {
        Ride ride = rideRepo.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        ride.setDriverId(driverId);
        ride.setStatus(RideStatus.RIDE_ACCEPTED);
        rideRepo.save(ride);
    }
}
