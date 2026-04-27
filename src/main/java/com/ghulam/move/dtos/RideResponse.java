package com.ghulam.move.dtos;

import com.ghulam.move.enums.RideStatus;

import java.time.LocalDateTime;

public record RideResponse(
        String id,
        String customerId,
        String driverId,
        double pickupLongitude,
        double pickupLatitude,
        String pickupAddress,
        double dropLongitude,
        double dropLatitude,
        String dropAddress,
        RideStatus status,
        double fare,
        LocalDateTime createdTimestamp,
        LocalDateTime updatedTimestamp,
        LocalDateTime rideStartedTimestamp,
        LocalDateTime rideCompletedTimestamp
) {
}
