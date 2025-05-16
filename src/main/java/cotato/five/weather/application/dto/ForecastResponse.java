package cotato.five.weather.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ForecastResponse(
        List<ForecastItem> list
) {
    public record ForecastItem(
            @JsonProperty("dt") long dt,
            @JsonProperty("main") Main main,
            @JsonProperty("weather") List<Weather> weather
    ) {}

    public record Main(
            double temp,
            int humidity
    ) {}

    public record Weather(
            String main
    ) {}
}