package cotato.five.weather.application.service;

import cotato.five.weather.application.dto.WeatherDailyResponse;
import cotato.five.weather.application.port.in.WeatherClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherClient weatherClient;

    public WeatherDailyResponse getDailyWeather(double lat, double lon) {
        return weatherClient.getDailyWeather(lat, lon);
    }
}
