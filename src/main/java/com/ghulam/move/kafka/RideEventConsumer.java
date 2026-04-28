package com.ghulam.move.kafka;

import com.ghulam.move.service.MatchingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RideEventConsumer {

    private final MatchingService matchingService;

    public void consumeRideRequest(RideRequestedEvent event) {
        matchingService.assignDriverForRide(event);
    }

}
