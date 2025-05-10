package cotato.five.weather.exception;

import cotato.five.weather.common.FailureResponse;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final FailureResponse failureResponse;

    CustomException(FailureResponse failureResponse) {
        super(failureResponse.getMessage());
        this.failureResponse = failureResponse;
    }
}
