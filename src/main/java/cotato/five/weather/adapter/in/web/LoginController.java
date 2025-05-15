package cotato.five.weather.adapter.in.web;

import cotato.five.weather.adapter.in.dto.request.LoginRequest;
import cotato.five.weather.application.port.in.LoginCommand;
import cotato.five.weather.common.ApiResponse;
import cotato.five.weather.common.BaseResponse;
import cotato.five.weather.common.SuccessResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "auth")
public class LoginController {
    private final LoginCommand loginCommand;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<?>> login(@RequestBody @Valid LoginRequest request) {
        UUID id = loginCommand.login(request);
        return ApiResponse.success(SuccessResponse.LOGGED_IN, generateCookie(id.toString()));
    }

    private ResponseCookie generateCookie(String id) {
        return ResponseCookie.from("id", id)
                .httpOnly(true)
                .secure(true)
                .maxAge(3600)
                .path("/")
                .sameSite("Strict")
                .build();
    }
}
