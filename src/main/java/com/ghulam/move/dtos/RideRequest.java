package com.ghulam.move.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RideRequest(
        @NotBlank
        String customerId,
        @NotNull
        double pickupLongitude,
        @NotNull
        double pickupLatitude,
        @NotBlank
        String pickupAddress,
        @NotNull
        double dropLongitude,
        @NotNull
        double dropLatitude,
        @NotBlank
        String dropAddress
) {
}
