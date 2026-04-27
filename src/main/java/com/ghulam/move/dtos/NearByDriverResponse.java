package com.ghulam.move.dtos;

public record NearByDriverResponse(
        String driverId,
        double longitude,
        double latitude,
        double distance
) {
}
