package com.ghulam.move.service;

import com.ghulam.move.dtos.RideRequest;
import com.ghulam.move.dtos.RideResponse;
import com.ghulam.move.kafka.RideRequestedEvent;
import com.ghulam.move.repo.RideRepo;
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

    public RideResponse requestNewRide(RideRequest rideRequest) {
        return null; // TODO
    }
}
