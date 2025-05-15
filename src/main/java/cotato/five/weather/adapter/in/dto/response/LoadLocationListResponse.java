package cotato.five.weather.adapter.in.dto.response;

import java.util.List;

public record LoadLocationListResponse(
        List<LoadLocationResponse> locations
) {
}
