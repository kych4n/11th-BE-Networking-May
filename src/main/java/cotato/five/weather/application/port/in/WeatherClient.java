package cotato.five.weather.application.port.in;

import cotato.five.weather.application.dto.WeatherDailyResponse;

public interface WeatherClient {
    WeatherDailyResponse getDailyWeather(double lat, double lon);
}
