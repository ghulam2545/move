package com.ghulam.move.kafka;

import com.ghulam.move.controller.KafkaStreamController;
import com.ghulam.move.kafka.event.RideRequestedEvent;
import com.ghulam.move.service.MatchingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.ghulam.move.constant.Constants.RIDE_REQUESTED_TOPIC;

@Slf4j
@Service
@RequiredArgsConstructor
public class RideEventConsumer {

    private final MatchingService matchingService;
    private final KafkaStreamController kafkaStreamController;

    @KafkaListener(topics = RIDE_REQUESTED_TOPIC, groupId = "move-group")
    public void consumeRideRequest(RideRequestedEvent event) {
        log.info("Received RideRequestedEvent for rideId: {}", event.id());

        kafkaStreamController.sendEvent(event);
        matchingService.assignDriverForRide(event);
    }

}
