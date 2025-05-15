package cotato.five.weather.adapter.in.web;

import cotato.five.weather.application.port.in.LoadLocationDetailQuery;
import cotato.five.weather.common.ApiResponse;
import cotato.five.weather.common.BaseResponse;
import cotato.five.weather.common.SuccessResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "locations")
public class LoadLocationDetailController {
    private final LoadLocationDetailQuery loadLocationDetailQuery;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<?>> load(@PathVariable(name = "id") Long id,
                                                @CookieValue("id") UUID memberId) {
        return ApiResponse.success(SuccessResponse.RETRIEVED_DETAIL_LOCATION,
                loadLocationDetailQuery.load(id, memberId));
    }
}
