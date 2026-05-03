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
    public ResponseEntity<RideResponse> requestRide(@RequestBody @Valid RideRequest rideRequest) {
        RideResponse rideResponse = rideService.requestRide(rideRequest);
        return ResponseEntity.ok(rideResponse);
    }

    @PostMapping(path = "/get")
    public ResponseEntity<RideResponse> getRideById(@RequestBody @Valid String rideId) {
        RideResponse rideResponse = rideService.getRideById(rideId);
        return ResponseEntity.ok(rideResponse);
    }

    @PostMapping(path = "/getAll")
    public ResponseEntity<Set<RideResponse>> getRidesByCustomerId(@RequestBody @Valid String customerId) {
        Set<RideResponse> response = rideService.getRidesByCustomerId(customerId);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/start")
    public ResponseEntity<RideResponse> startRide(@RequestBody @Valid String rideId) {
        RideResponse response = rideService.startRide(rideId);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/complete")
    public ResponseEntity<RideResponse> completeRide(@RequestBody @Valid String rideId) {
        RideResponse response = rideService.completeRide(rideId);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/cancel")
    public ResponseEntity<RideResponse> cancelRide(@RequestBody @Valid String rideId) {
        RideResponse response = rideService.cancelRide(rideId);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/database")
    public ResponseEntity<Set<RideResponse>> database() {
        Set<RideResponse> out = rideService.database();
        return ResponseEntity.ok(out);
    }
}
