package cotato.five.weather.exception;

import cotato.five.weather.common.FailureResponse;

public class MethodArgumentNotValidException extends CustomException {
    public MethodArgumentNotValidException(FailureResponse failureResponse) {
        super(failureResponse);
    }
}
