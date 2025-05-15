package cotato.five.weather.adapter.in.web;

import cotato.five.weather.application.port.in.RemoveLocationCommand;
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
public class RemoveLocationController {
    private final RemoveLocationCommand removeLocationCommand;

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<?>> remove(@PathVariable(name = "id") Long id,
                                                  @CookieValue("id") UUID memberId) {
        removeLocationCommand.remove(id, memberId);
        return ApiResponse.success(SuccessResponse.DELETED_LOCATION);
    }
}
