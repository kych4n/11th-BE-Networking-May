package cotato.five.weather.exception;

import cotato.five.weather.common.FailureResponse;

public class BadRequestException extends CustomException {
    public BadRequestException(FailureResponse failureResponse) {
        super(failureResponse);
    }
}