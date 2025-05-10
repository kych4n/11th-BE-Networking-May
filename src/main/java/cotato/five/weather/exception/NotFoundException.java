package cotato.five.weather.exception;

import cotato.five.weather.common.FailureResponse;

public class NotFoundException extends CustomException {
    public NotFoundException(FailureResponse failureResponse) {
        super(failureResponse);
    }
}
