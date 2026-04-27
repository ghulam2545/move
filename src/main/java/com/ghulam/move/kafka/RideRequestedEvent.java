package com.ghulam.move.kafka;

public record RideRequestedEvent(
        String id,
        String customerId,
        String driverId,
        double pickupLongitude,
        double pickupLatitude,
        String pickupAddress,
        double dropLongitude,
        double dropLatitude,
        String dropAddress
) {
}
