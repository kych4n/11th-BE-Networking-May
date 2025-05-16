package cotato.five.weather.adapter.out.persistence;

import cotato.five.weather.application.dto.*;
import cotato.five.weather.application.port.in.WeatherClient;
import cotato.five.weather.exception.BadRequestException;
import cotato.five.weather.exception.CustomException;
import cotato.five.weather.exception.MethodArgumentNotValidException;
import cotato.five.weather.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

import static cotato.five.weather.common.FailureResponse.*;

@Service
@RequiredArgsConstructor
public class OpenWeatherClient implements WeatherClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_KEY = "2cad1ffa7b8e3e31918a57701cb33774";

    private final OpenWeatherForecastClient forecastClient;

    @Override
    public WeatherDailyResponse getDailyWeather(double lat, double lon) {
        try {
            // One Call 3.0 현재 날씨
            String url = UriComponentsBuilder.fromHttpUrl("https://api.openweathermap.org/data/3.0/onecall")
                    .queryParam("lat", lat)
                    .queryParam("lon", lon)
                    .queryParam("exclude", "minutely,hourly,daily,alerts")
                    .queryParam("units", "metric")
                    .queryParam("appid", API_KEY)
                    .toUriString();

            OpenWeatherResponse res = restTemplate.getForObject(url, OpenWeatherResponse.class);
            var current = res.current();

            String airUrl = UriComponentsBuilder.fromHttpUrl("https://api.openweathermap.org/data/2.5/air_pollution")
                    .queryParam("lat", lat)
                    .queryParam("lon", lon)
                    .queryParam("appid", API_KEY)
                    .toUriString();

            // 미세먼지, 초 미세먼지 (pm10, pm2_5)
            AirPollutionResponse airRes = restTemplate.getForObject(airUrl, AirPollutionResponse.class);
            var components = airRes.list().get(0).components();

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
                    .sunrise(LocalDateTime.ofInstant(Instant.ofEpochSecond(current.sunrise()), ZoneId.of("Asia/Seoul")))
                    .build();
        } catch (HttpClientErrorException | ResourceAccessException e) {
            throw new UnauthorizedException(EXTERNAL_SERVER_ERROR); // 외부 서버 오류
        } catch (Exception e) {
            throw new BadRequestException(INTERNAL_SEVER_ERROR); // 내부 서버 오류
        }
    }

    @Override
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

//            if (response == null || response.hourly() == null) {
//                throw new MethodArgumentNotValidException(INVALID_DATA);
//            }

            List<WeatherHourlyData> hourly = response.hourly().stream()
                    .limit(24)
                    .map(hour -> new WeatherHourlyData(
                            LocalDateTime.ofInstant(Instant.ofEpochSecond(hour.dt()), ZoneId.of("Asia/Seoul")),
                            hour.weather().get(0).main(),
                            hour.temp()
                    )).toList();

            return new WeatherHourlyResponse(hourly);

        } catch (HttpClientErrorException | ResourceAccessException e) {
            throw new UnauthorizedException(EXTERNAL_SERVER_ERROR); // 외부 서버 오류
        } catch (Exception e) {
            throw new BadRequestException(INTERNAL_SEVER_ERROR); // 내부 서버 오류
        }
    }

    public WeatherWeeklyResponse getWeeklyWeather(double lat, double lon) {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalTime now = LocalTime.now(ZoneId.of("Asia/Seoul"));

        List<WeatherDailyForecast> weekly = new ArrayList<>();

        if (now.isBefore(LocalTime.NOON)) {
            // 오전: forecast로 오늘 포함 5일
            List<WeatherDailyForecast> forecasts = forecastClient.getForecasts(lat, lon, today);
            if (forecasts.size() > 5) {
                forecasts = forecasts.subList(0, 5);
            }
            weekly.addAll(forecasts);
        } else {
            // 오후: 오늘 오전은 오늘 날씨로, 나머지 오늘 오후와 4일은 forecast
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

            List<WeatherDailyForecast> futureForecasts = forecasts.subList(1, Math.min(5, forecasts.size()));
            weekly.add(todayForecast);
            weekly.addAll(futureForecasts);
        }


        return new WeatherWeeklyResponse(weekly);
    }
}