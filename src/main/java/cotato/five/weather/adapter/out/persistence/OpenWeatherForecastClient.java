package cotato.five.weather.adapter.out.persistence;

import cotato.five.weather.application.dto.ForecastResponse;
import cotato.five.weather.application.dto.WeatherDailyForecast;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static cotato.five.weather.common.FailureResponse.EXTERNAL_SERVER_ERROR;
import static cotato.five.weather.common.FailureResponse.INTERNAL_SEVER_ERROR;

@Service
@RequiredArgsConstructor
public class OpenWeatherForecastClient {
    // 미래 예보

    private final RestTemplate restTemplate;
    @Value("${openweather.api.key}")
    private String API_KEY;
    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Seoul");

    public List<WeatherDailyForecast> getForecasts(double lat, double lon, LocalDate startDate) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl("https://api.openweathermap.org/data/2.5/forecast")
                    .queryParam("lat", lat)
                    .queryParam("lon", lon)
                    .queryParam("units", "metric")
                    .queryParam("appid", API_KEY)
                    .toUriString();

            ForecastResponse res = restTemplate.getForObject(url, ForecastResponse.class);

            LocalDate endDate = startDate.plusDays(4);

            Map<LocalDate, List<ForecastResponse.ForecastItem>> grouped = new HashMap<>();
            for (ForecastResponse.ForecastItem item : res.list()) {
                LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(item.dt()), ZONE_ID);
                LocalDate date = dateTime.toLocalDate();

                grouped.computeIfAbsent(date, k -> new ArrayList<>()).add(item);
            }


            List<WeatherDailyForecast> result = new ArrayList<>();


            List<LocalDate> sortedDates = new ArrayList<>(grouped.keySet());
            Collections.sort(sortedDates);

            for (LocalDate date : sortedDates) {
                if (date.isBefore(startDate) || date.isAfter(endDate)) {
                    continue;
                }

                List<ForecastResponse.ForecastItem> items = grouped.get(date);
                List<ForecastResponse.ForecastItem> morningItems = new ArrayList<>();
                List<ForecastResponse.ForecastItem> afternoonItems = new ArrayList<>();

                for (ForecastResponse.ForecastItem item : items) {
                    int hour = LocalDateTime.ofInstant(Instant.ofEpochSecond(item.dt()), ZONE_ID).getHour();
                    if (hour < 12) {
                        morningItems.add(item);
                    } else {
                        afternoonItems.add(item);
                    }
                }

                WeatherDailyForecast forecast = new WeatherDailyForecast(
                        date.toString(),
                        avgHalfDay(morningItems),
                        avgHalfDay(afternoonItems)
                );

                result.add(forecast);
            }

            return result;
        } catch (HttpClientErrorException | ResourceAccessException e) {
            throw new UnauthorizedException(EXTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new BadRequestException(INTERNAL_SEVER_ERROR);
        }
    }


    private WeatherDailyForecast.HalfDayData avgHalfDay(List<ForecastResponse.ForecastItem> items) {
        if (items.isEmpty()) {
            return new WeatherDailyForecast.HalfDayData(0, 0, "Unknown");
        }

        // 평균 기온
        double tempSum = 0.0;
        for (ForecastResponse.ForecastItem item : items) {
            tempSum += item.main().temp();
        }
        double avgTemp = tempSum / items.size();

        // 평균 습도
        int humiditySum = 0;
        for (ForecastResponse.ForecastItem item : items) {
            humiditySum += item.main().humidity();
        }
        int avgHumidity = (int) ((double) humiditySum / items.size());

        // 가장 많이 등장한 날씨
        Map<String, Integer> weatherCountMap = new HashMap<>();
        for (ForecastResponse.ForecastItem item : items) {
            String mainWeather = item.weather().get(0).main();
            weatherCountMap.put(mainWeather, weatherCountMap.getOrDefault(mainWeather, 0) + 1);
        }

        String mostCommonWeather = "Unknown";
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : weatherCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostCommonWeather = entry.getKey();
            }
        }

        return new WeatherDailyForecast.HalfDayData(avgTemp, avgHumidity, mostCommonWeather);
    }
}

