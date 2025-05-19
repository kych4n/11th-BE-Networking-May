package cotato.five.weather.adapter.in.web;

import cotato.five.weather.common.ApiResponse;
import cotato.five.weather.common.BaseResponse;
import cotato.five.weather.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "auth")
public class LogoutController {
    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<?>> logout() {
        return ApiResponse.success(SuccessResponse.LOGGED_OUT, expireCookie());
    }

    private ResponseCookie expireCookie() {
        return ResponseCookie.from("id", "")
                .httpOnly(true)
                .secure(true)
                .maxAge(0)
                .path("/")
                .sameSite("None")
                .build();
    }
}
