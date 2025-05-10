package cotato.five.weather.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BaseResponse<T> {
    private final String code;
    private final String message;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private final T data;

    public static <T> BaseResponse<?> of(SuccessResponse successResponse) {
        return BaseResponse.builder()
                .code(successResponse.getCode())
                .message(successResponse.getMessage())
                .build();
    }

    public static <T> BaseResponse<?> of(SuccessResponse successResponse, T data) {
        return BaseResponse.builder()
                .code(successResponse.getCode())
                .message(successResponse.getMessage())
                .data(data)
                .build();
    }

    public static <T> BaseResponse<?> of(FailureResponse failureResponse) {
        return BaseResponse.builder()
                .code(failureResponse.getCode())
                .message(failureResponse.getMessage())
                .build();
    }
}
