package com.ghulam.move;

import com.ghulam.move.dtos.DriverLocationRequest;
import com.ghulam.move.service.LocationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class MoveApplication {

    private final LocationService locationService;

    public static void main(String[] args) {
        SpringApplication.run(MoveApplication.class, args);
    }

    @PostConstruct
    public void addFewDrivers() {
        locationService.updateDriverLocation(new DriverLocationRequest("driver1", 74.0060, 40.7128));
        locationService.updateDriverLocation(new DriverLocationRequest("driver2", 118.2437, 34.0522));
        locationService.updateDriverLocation(new DriverLocationRequest("driver3", 87.6298, 41.8781));
        locationService.updateDriverLocation(new DriverLocationRequest("driver4", 95.3698, 29.7604));
        locationService.updateDriverLocation(new DriverLocationRequest("driver5", 104.9903, 39.7392));
    }

}
