package cotato.five.weather.application.dto;

import java.util.List;

public record WeatherWeeklyResponse(
        List<WeatherDailyForecast> weekly
) {}
