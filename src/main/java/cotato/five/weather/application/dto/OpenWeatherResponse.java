package cotato.five.weather.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OpenWeatherResponse(
        Current current
) {
    public record Current(
            double temp,
            double feels_like,
            int humidity,
            double wind_speed,
            int wind_deg,
            double uvi,
            long sunrise,
            List<Weather> weather
    ) {}

    public record Weather(
            String main
    ) {}
}
