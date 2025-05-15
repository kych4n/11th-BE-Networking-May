package cotato.five.weather.adapter.in.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterLocationRequest(
        @NotBlank
        String name,
        @NotNull
        Double latitude,
        @NotNull
        Double longitude
) {
}
