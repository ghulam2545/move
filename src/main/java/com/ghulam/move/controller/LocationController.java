package com.ghulam.move.controller;

import com.ghulam.move.dtos.DriverLocationRequest;
import com.ghulam.move.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping(path = "/driver/update")
    public ResponseEntity<String> updateDriverLocation(@RequestBody @Valid DriverLocationRequest driverLocationRequest) {
        locationService.updateDriverLocation(driverLocationRequest);
        return ResponseEntity.ok("Location of driver [%s] has been updated successfully.".formatted(driverLocationRequest.driverId()));
    }
}
