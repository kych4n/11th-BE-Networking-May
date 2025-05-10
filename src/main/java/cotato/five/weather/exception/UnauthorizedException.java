package cotato.five.weather.exception;

import cotato.five.weather.common.FailureResponse;

public class UnauthorizedException extends CustomException {
    public UnauthorizedException(FailureResponse failureResponse) {
        super(failureResponse);
    }
}
