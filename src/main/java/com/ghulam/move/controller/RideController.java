package com.ghulam.move.controller;


import com.ghulam.move.dtos.RideRequest;
import com.ghulam.move.dtos.RideResponse;
import com.ghulam.move.service.RideService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping(value = "/api/ride")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @PostMapping(path = "/request")
    public ResponseEntity<RideResponse> requestARide(@RequestBody @Valid RideRequest rideRequest) {
        RideResponse rideResponse = rideService.requestNewRide(rideRequest);
        return ResponseEntity.ok(rideResponse);
    }

    @PostMapping(path = "/get")
    public ResponseEntity<RideResponse> getRideById(@RequestBody @Valid String customerId) {
        return null; // TODO
    }

    @PostMapping(path = "/getAll")
    public ResponseEntity<Set<RideResponse>> getRidesByCustomerId(@RequestBody @Valid String customerId) {
        return null; // TODO
    }

    @PostMapping(path = "/start")
    public ResponseEntity<String> startRide(@RequestBody @Valid String rideId) {
        return null; // TODO
    }

    @PostMapping(path = "/complete")
    public ResponseEntity<String> completeRide(@RequestBody @Valid String rideId) {
        return null; // TODO
    }

    @PostMapping(path = "/cancel")
    public ResponseEntity<String> cancelRide(@RequestBody @Valid String rideId) {
        return null; // TODO
    }
}
