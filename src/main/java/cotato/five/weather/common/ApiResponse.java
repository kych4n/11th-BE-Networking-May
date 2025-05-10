package cotato.five.weather.common;

import org.springframework.http.ResponseEntity;

public class ApiResponse {
    public static <T> ResponseEntity<BaseResponse<?>> success(SuccessResponse successResponse) {
        return ResponseEntity.status(successResponse.getStatus()).body(BaseResponse.of(successResponse));
    }

    public static <T> ResponseEntity<BaseResponse<?>> success(SuccessResponse successResponse, T data) {
        return ResponseEntity.status(successResponse.getStatus()).body(BaseResponse.of(successResponse, data));
    }

    public static <T> ResponseEntity<BaseResponse<?>> failure(FailureResponse failureResponse) {
        return ResponseEntity.status(failureResponse.getStatus()).body(BaseResponse.of(failureResponse));
    }
}
