package cotato.five.weather.adapter.in.web;

import cotato.five.weather.application.dto.WeatherDailyResponse;
import cotato.five.weather.application.dto.WeatherHourlyResponse;
import cotato.five.weather.application.dto.WeatherWeeklyResponse;
import cotato.five.weather.application.WeatherService;
import cotato.five.weather.common.ApiResponse;
import cotato.five.weather.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static cotato.five.weather.common.SuccessResponse.*;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/daily")
    public HttpEntity<BaseResponse<?>> getDailyWeather(
            @RequestParam double latitude,
            @RequestParam double longitude
    ) {
        WeatherDailyResponse data = weatherService.getDailyWeather(latitude, longitude);
        return ApiResponse.success(RETRIEVED_DETAIL_DAILY_WEATHER, data);
    }

    @GetMapping("/hourly")
    public ResponseEntity<?> getHourlyWeather(@RequestParam double latitude,
                                              @RequestParam double longitude) {
        WeatherHourlyResponse data = weatherService.getHourlyWeather(latitude, longitude);
        return ApiResponse.success(RETRIEVED_HOURLY_WEATHER, data);
    }

    @GetMapping("/weekly")
    public ResponseEntity<?> getWeeklyWeather(@RequestParam double latitude,
                                              @RequestParam double longitude) {
        WeatherWeeklyResponse data = weatherService.getWeeklyWeather(latitude, longitude);
        return ApiResponse.success(RETRIEVED_WEEKLY_WEATHER, data);
    }
}
