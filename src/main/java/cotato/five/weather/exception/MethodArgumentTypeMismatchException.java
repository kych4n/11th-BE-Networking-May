package cotato.five.weather.exception;

import cotato.five.weather.common.FailureResponse;

public class MethodArgumentTypeMismatchException extends CustomException {
    public MethodArgumentTypeMismatchException(FailureResponse failureResponse) {
        super(failureResponse);
    }
}
