package cotato.five.weather.adapter.in.web;

import cotato.five.weather.application.port.in.CheckAuthorizationQuery;
import cotato.five.weather.common.ApiResponse;
import cotato.five.weather.common.BaseResponse;
import cotato.five.weather.common.SuccessResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "auth")
public class CheckAuthorizationController {
    private final CheckAuthorizationQuery checkAuthorizationQuery;

    @GetMapping("/check")
    public ResponseEntity<BaseResponse<?>> check(@CookieValue("id") UUID id) {
        return ApiResponse.success(SuccessResponse.LOGGED_IN, checkAuthorizationQuery.check(id));
    }
}
