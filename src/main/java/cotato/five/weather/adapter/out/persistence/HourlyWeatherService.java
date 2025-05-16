package cotato.five.weather.adapter.out.persistence;

import cotato.five.weather.application.dto.OpenWeatherResponse;
import cotato.five.weather.application.dto.WeatherHourlyData;
import cotato.five.weather.application.dto.WeatherHourlyResponse;
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
import java.util.List;

import static cotato.five.weather.common.FailureResponse.EXTERNAL_SERVER_ERROR;
import static cotato.five.weather.common.FailureResponse.INTERNAL_SEVER_ERROR;

@Service
@RequiredArgsConstructor
public class HourlyWeatherService {

    private final RestTemplate restTemplate;
    @Value("${openweather.api.key}")
    private String API_KEY;
    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Seoul");

    public WeatherHourlyResponse getHourlyWeather(double lat, double lon) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl("https://api.openweathermap.org/data/3.0/onecall")
                    .queryParam("lat", lat)
                    .queryParam("lon", lon)
                    .queryParam("exclude", "current,minutely,daily,alerts")
                    .queryParam("units", "metric")
                    .queryParam("appid", API_KEY)
                    .toUriString();

            OpenWeatherResponse response = restTemplate.getForObject(url, OpenWeatherResponse.class);

            List<WeatherHourlyData> hourly = response.hourly().stream()
                    .limit(24)
                    .map(hour -> new WeatherHourlyData(
                            LocalDateTime.ofInstant(Instant.ofEpochSecond(hour.dt()), ZONE_ID),
                            hour.weather().get(0).main(),
                            hour.temp()
                    )).toList();

            return new WeatherHourlyResponse(hourly);

        } catch (HttpClientErrorException | ResourceAccessException e) {
            throw new UnauthorizedException(EXTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new BadRequestException(INTERNAL_SEVER_ERROR);
        }
    }
}
