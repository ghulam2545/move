package com.ghulam.move.dtos;

import jakarta.validation.constraints.NotNull;

public record NearByDriverRequest(
        @NotNull
        double longitude,
        @NotNull
        double latitude,
        @NotNull
        double radius
) {
}
