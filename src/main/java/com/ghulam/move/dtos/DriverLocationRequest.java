package com.ghulam.move.dtos;

import jakarta.validation.constraints.NotNull;

public record DriverLocationRequest(
        @NotNull
        String driverId,
        @NotNull
        double longitude,
        @NotNull
        double latitude
) {
}
