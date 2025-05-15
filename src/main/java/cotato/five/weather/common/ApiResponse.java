package cotato.five.weather.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

public class ApiResponse {
    public static <T> ResponseEntity<BaseResponse<?>> success(SuccessResponse successResponse) {
        return ResponseEntity.status(successResponse.getStatus()).body(BaseResponse.of(successResponse));
    }

    public static <T> ResponseEntity<BaseResponse<?>> success(SuccessResponse successResponse, ResponseCookie cookie) {
        return ResponseEntity.status(successResponse.getStatus()).header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(BaseResponse.of(successResponse));
    }

    public static <T> ResponseEntity<BaseResponse<?>> success(SuccessResponse successResponse, T data) {
        return ResponseEntity.status(successResponse.getStatus()).body(BaseResponse.of(successResponse, data));
    }

    public static <T> ResponseEntity<BaseResponse<?>> failure(FailureResponse failureResponse) {
        return ResponseEntity.status(failureResponse.getStatus()).body(BaseResponse.of(failureResponse));
    }
}
