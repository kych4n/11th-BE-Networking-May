package cotato.five.weather.application.dto;

public record WeatherDailyForecast(
        String date,
        HalfDayData morning,
        HalfDayData afternoon
) {
    public record HalfDayData(
            double temp,
            int humidity,
            String weather
    ) {}
}