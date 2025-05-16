package cotato.five.weather.adapter.out.persistence;

import cotato.five.weather.application.dto.ForecastResponse;
import cotato.five.weather.application.dto.WeatherDailyForecast;
import cotato.five.weather.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cotato.five.weather.common.FailureResponse.INVALID_DATA;

@Service
@RequiredArgsConstructor
public class OpenWeatherForecastClient {
    // 미래 예보

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_KEY = "2cad1ffa7b8e3e31918a57701cb33774";
    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Seoul");

    public List<WeatherDailyForecast> getForecasts(double lat, double lon, LocalDate startDate) {
        String url = UriComponentsBuilder.fromHttpUrl("https://api.openweathermap.org/data/2.5/forecast")
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("units", "metric")
                .queryParam("appid", API_KEY)
                .toUriString();

        ForecastResponse res = restTemplate.getForObject(url, ForecastResponse.class);

//        if (res == null || res.list() == null) {
//            throw new BadRequestException(INVALID_DATA);
//        }

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
                    morningItems.add(item); // 오전: 00~11시
                } else {
                    afternoonItems.add(item); // 오후: 12~23시
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

