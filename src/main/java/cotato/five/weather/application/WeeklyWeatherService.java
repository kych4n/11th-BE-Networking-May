package cotato.five.weather.application;

import cotato.five.weather.application.dto.OpenWeatherResponse;
import cotato.five.weather.application.dto.WeatherDailyForecast;
import cotato.five.weather.application.dto.WeatherWeeklyResponse;
import cotato.five.weather.exception.BadRequestException;
import cotato.five.weather.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static cotato.five.weather.common.FailureResponse.EXTERNAL_SERVER_ERROR;
import static cotato.five.weather.common.FailureResponse.INTERNAL_SEVER_ERROR;

@Service
@RequiredArgsConstructor
public class WeeklyWeatherService {

    private final RestTemplate restTemplate;
    @Value("${openweather.api.key}")
    private String API_KEY;
    private final OpenWeatherForecastClient forecastClient;

    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Seoul");

    public WeatherWeeklyResponse getWeeklyWeather(double lat, double lon) {
        LocalDate today = LocalDate.now(ZONE_ID);
        LocalTime now = LocalTime.now(ZONE_ID);

        List<WeatherDailyForecast> weekly = new ArrayList<>();

        if (now.isBefore(LocalTime.NOON)) {
            List<WeatherDailyForecast> forecasts = forecastClient.getForecasts(lat, lon, today);
            weekly.addAll(forecasts.subList(0, Math.min(5, forecasts.size())));
        } else {
            try {
                String url = UriComponentsBuilder.fromHttpUrl("https://api.openweathermap.org/data/3.0/onecall")
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("exclude", "minutely,hourly,daily,alerts")
                        .queryParam("units", "metric")
                        .queryParam("appid", API_KEY)
                        .toUriString();

                OpenWeatherResponse res = restTemplate.getForObject(url, OpenWeatherResponse.class);
                var current = res.current();

                WeatherDailyForecast.HalfDayData morning = new WeatherDailyForecast.HalfDayData(
                        current.temp(),
                        current.humidity(),
                        current.weather().get(0).main()
                );

                List<WeatherDailyForecast> forecasts = forecastClient.getForecasts(lat, lon, today);
                WeatherDailyForecast todayForecastFromForecast = forecasts.get(0);
                WeatherDailyForecast.HalfDayData afternoon = todayForecastFromForecast.afternoon();

                WeatherDailyForecast todayForecast = new WeatherDailyForecast(
                        today.toString(), morning, afternoon
                );

                weekly.add(todayForecast);
                weekly.addAll(forecasts.subList(1, Math.min(5, forecasts.size())));
            } catch (HttpClientErrorException | ResourceAccessException e) {
                throw new UnauthorizedException(EXTERNAL_SERVER_ERROR);
            } catch (Exception e) {
                throw new BadRequestException(INTERNAL_SEVER_ERROR);
            }
        }

        return new WeatherWeeklyResponse(weekly);
    }
}
