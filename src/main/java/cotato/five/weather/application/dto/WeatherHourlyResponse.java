package cotato.five.weather.application.dto;


import java.util.List;

public record WeatherHourlyResponse(
        List<WeatherHourlyData> hourly
) {}
