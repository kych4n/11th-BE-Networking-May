package cotato.five.weather.adapter.in.web;

import cotato.five.weather.adapter.in.dto.request.RegisterLocationRequest;
import cotato.five.weather.application.port.in.RegisterLocationCommand;
import cotato.five.weather.common.ApiResponse;
import cotato.five.weather.common.BaseResponse;
import cotato.five.weather.common.SuccessResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "locations")
public class RegisterLocationController {
    private final RegisterLocationCommand registerLocationCommand;

    @PostMapping
    public ResponseEntity<BaseResponse<?>> register(@RequestBody @Valid RegisterLocationRequest request,
                                                    @CookieValue("id") UUID id) {
        registerLocationCommand.register(request, id);
        return ApiResponse.success(SuccessResponse.REGISTERED_LOCATION);
    }
}
