package cotato.five.weather.adapter.in.dto.response;

public record LoadLocationDetailResponse(
        Long id,
        String name,
        Double latitude,
        Double longitude
) {
}
