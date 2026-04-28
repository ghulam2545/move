package com.ghulam.move.service;

import com.ghulam.move.constant.Constants;
import com.ghulam.move.dtos.NearByDriverRequest;
import com.ghulam.move.dtos.NearByDriverResponse;
import com.ghulam.move.kafka.RideMatchedEvent;
import com.ghulam.move.kafka.RideRequestedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchingService {

    private final LocationService locationService;
    private final KafkaTemplate<String, RideMatchedEvent> kafkaTemplate;

    public void assignDriverForRide(RideRequestedEvent event) {
        Set<NearByDriverResponse> nearbyDrivers = locationService.getNearbyDrivers(
                new NearByDriverRequest(
                        event.pickupLongitude(),
                        event.pickupLatitude(),
                        Constants.DEFAULT_SEARCH_RADIUS_KM
                )
        );

        if (nearbyDrivers.isEmpty()) {
            log.info("No nearby drivers found");
            return;
        }

        Optional<NearByDriverResponse> bestDriver = findBestDriver(nearbyDrivers);
        if (bestDriver.isEmpty()) {
            log.info("No best driver found");
            return;
        }

        NearByDriverResponse driver = bestDriver.get();
        RideMatchedEvent matchedEvent = new RideMatchedEvent(
                event.id(),
                event.customerId(),
                driver.driverId(),
                driver.longitude(),
                driver.latitude(),
                driver.distance()
        );

        kafkaTemplate.send(Constants.RIDE_MATCHED_TOPIC, event.id(), matchedEvent);
        log.info("Ride matched event sent for Id: {}", matchedEvent.id());
    }

    private Optional<NearByDriverResponse> findBestDriver(
            Set<NearByDriverResponse> drivers) {

        double distanceWeight = 0.7;
        double ratingWeight = 0.3;

        return drivers.stream()
                .max(Comparator.comparingDouble(driver -> {
                    double distanceScore = 1.0 / (driver.distance() + 0.1);
                    double simulatedRating = 4.0 + Math.random();
                    return (distanceScore * distanceWeight)
                            + (simulatedRating * ratingWeight);
                }));
    }
}
