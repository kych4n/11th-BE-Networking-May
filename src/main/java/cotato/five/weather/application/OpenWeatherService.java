package cotato.five.weather.application;

import cotato.five.weather.application.dto.*;
import cotato.five.weather.application.port.in.WeatherClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenWeatherService implements WeatherClient {

    private final CurrentWeatherService currentService;
    private final HourlyWeatherService hourlyService;
    private final WeeklyWeatherService weeklyService;

    @Override
    public WeatherDailyResponse getDailyWeather(double lat, double lon) {
        return currentService.getDailyWeather(lat, lon);
    }

    @Override
    public WeatherHourlyResponse getHourlyWeather(double lat, double lon) {
        return hourlyService.getHourlyWeather(lat, lon);
    }

    @Override
    public WeatherWeeklyResponse getWeeklyWeather(double lat, double lon) {
        return weeklyService.getWeeklyWeather(lat, lon);
    }
}
