package cotato.five.weather.application.dto;

import java.time.LocalDateTime;

public record WeatherHourlyData(
        LocalDateTime time,
        String weather,
        double temp
) {}
