package com.ghulam.move.kafka;

public record RideMatchedEvent(
        String id,
        String customerId,
        String driverId,
        double driverLongitude,
        double driverLatitude,
        double distanceToCover
) {
}
