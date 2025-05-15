package cotato.five.weather.application.port.in;

import cotato.five.weather.application.dto.WeatherDailyResponse;
import cotato.five.weather.application.dto.WeatherHourlyResponse;

public interface WeatherClient {
    WeatherDailyResponse getDailyWeather(double lat, double lon);
    WeatherHourlyResponse getHourlyWeather(double lat, double lon);
}
