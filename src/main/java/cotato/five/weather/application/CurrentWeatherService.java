package cotato.five.weather.application;

import cotato.five.weather.application.dto.OpenWeatherResponse;
import cotato.five.weather.application.dto.WeatherDailyResponse;
import cotato.five.weather.exception.BadRequestException;
import cotato.five.weather.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static cotato.five.weather.common.FailureResponse.EXTERNAL_SERVER_ERROR;
import static cotato.five.weather.common.FailureResponse.INTERNAL_SEVER_ERROR;

@Service
@RequiredArgsConstructor
public class CurrentWeatherService {

    private final RestTemplate restTemplate;
    @Value("${openweather.api.key}")
    private String API_KEY;
    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Seoul");

    private final AirPollutionService airPollutionService;

    public WeatherDailyResponse getDailyWeather(double lat, double lon) {
        try {
            String url = UriComponentsBuilder
                    .fromHttpUrl("https://api.openweathermap.org/data/3.0/onecall")
                    .queryParam("lat", lat)
                    .queryParam("lon", lon)
                    .queryParam("exclude", "minutely,hourly,daily,alerts")
                    .queryParam("units", "metric")
                    .queryParam("appid", API_KEY)
                    .toUriString();

            OpenWeatherResponse res = restTemplate.getForObject(url, OpenWeatherResponse.class);
            var current = res.current();

            var components = airPollutionService.getComponents(lat, lon);

            return WeatherDailyResponse.builder()
                    .temp(current.temp())
                    .weather(current.weather().get(0).main())
                    .feelsLike(current.feels_like())
                    .humidity(current.humidity())
                    .windSpeed(current.wind_speed())
                    .windDeg(current.wind_deg())
                    .pm10((int) components.pm10())
                    .pm2_5((int) components.pm2_5())
                    .uvi(current.uvi())
                    .sunrise(LocalDateTime.ofInstant(Instant.ofEpochSecond(current.sunrise()), ZONE_ID))
                    .build();

        } catch (HttpClientErrorException | ResourceAccessException e) {
            throw new UnauthorizedException(EXTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new BadRequestException(INTERNAL_SEVER_ERROR);
        }
    }
}

