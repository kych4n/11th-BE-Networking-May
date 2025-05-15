package cotato.five.weather.adapter.in.web;

import cotato.five.weather.application.port.in.UnpinCommand;
import cotato.five.weather.common.ApiResponse;
import cotato.five.weather.common.BaseResponse;
import cotato.five.weather.common.SuccessResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "locations")
public class UnpinController {
    private final UnpinCommand unpinCommand;

    @DeleteMapping("/{id}/pin")
    public ResponseEntity<BaseResponse<?>> unpin(@PathVariable(name = "id") Long id, @CookieValue("id") UUID memberId) {
        unpinCommand.unpin(id, memberId);
        return ApiResponse.success(SuccessResponse.DELETED_PIN);
    }
}
