package com.ghulam.move.controller;

import com.ghulam.move.dtos.DriverLocationRequest;
import com.ghulam.move.dtos.NearByDriverRequest;
import com.ghulam.move.dtos.NearByDriverResponse;
import com.ghulam.move.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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

    @PostMapping(path = "/drivers/nearby")
    public ResponseEntity<Set<NearByDriverResponse>> getNearbyDrivers(@RequestBody @Valid NearByDriverRequest nearByDriverRequest) {
        Set<NearByDriverResponse> drivers = locationService.getNearbyDrivers(nearByDriverRequest);
        return ResponseEntity.ok(drivers);
    }

}
