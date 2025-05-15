package cotato.five.weather.adapter.in.web;

import cotato.five.weather.adapter.in.dto.request.ModifyLocationRequest;
import cotato.five.weather.application.port.in.ModifyLocationCommand;
import cotato.five.weather.common.ApiResponse;
import cotato.five.weather.common.BaseResponse;
import cotato.five.weather.common.SuccessResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "locations")
public class ModifyLocationController {
    private final ModifyLocationCommand modifyLocationCommand;

    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponse<?>> remove(@RequestBody @Valid ModifyLocationRequest request,
                                                  @PathVariable(name = "id") Long id,
                                                  @CookieValue("id") UUID memberId
    ) {
        modifyLocationCommand.modify(request, id, memberId);
        return ApiResponse.success(SuccessResponse.MODIFIED_LOCATION);
    }
}
