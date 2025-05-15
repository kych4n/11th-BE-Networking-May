package cotato.five.weather.adapter.in.web;

import cotato.five.weather.application.dto.WeatherDailyResponse;
import cotato.five.weather.application.service.WeatherService;
import cotato.five.weather.common.ApiResponse;
import cotato.five.weather.common.BaseResponse;
import cotato.five.weather.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static cotato.five.weather.common.SuccessResponse.RETRIEVED_DETAIL_DAILY_WEATHER;

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
}
