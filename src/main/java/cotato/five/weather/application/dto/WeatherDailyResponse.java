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
        String windDeg,
        String pm10,
        String pm2_5,
        String uvi,
        LocalDateTime sunrise
) {}
