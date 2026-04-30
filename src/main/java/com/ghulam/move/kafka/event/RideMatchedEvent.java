package com.ghulam.move.kafka.event;

public record RideMatchedEvent(
        String id,
        String customerId,
        String driverId,
        double driverLongitude,
        double driverLatitude,
        double distanceToCover
) {
}
