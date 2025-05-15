package cotato.five.weather.adapter.in.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModifyLocationRequest(
        @NotBlank
        String name,
        @NotNull
        Double latitude,
        @NotNull
        Double longitude
) {
}
