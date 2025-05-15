package cotato.five.weather.application.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record WeatherDailyResponse(
        double temp,

        String weather,
        double feelsLike,
        int humidity,
        double windSpeed,
        int windDeg,
        int pm10,
        int pm2_5,
        double uvi,
        LocalDateTime sunrise
) {}
