package cotato.five.weather.exception;

import cotato.five.weather.common.FailureResponse;

public class ConflictException extends CustomException {
    public ConflictException(FailureResponse failureResponse) {
        super(failureResponse);
    }
}