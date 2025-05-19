package cotato.five.weather.adapter.in.dto.response;

public record LoadLocationResponse(
        Long id,
        String name,
        Double latitude,
        Double longitude,
        Boolean isPinned
) {
}
