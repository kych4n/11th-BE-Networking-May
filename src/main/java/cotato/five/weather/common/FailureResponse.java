package cotato.five.weather.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FailureResponse {
    INTERNAL_SEVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-001", "내부 서버 오류"),
    EXTERNAL_SERVER_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "COMMON-002", "외부 서버 오류"),
    INVALID_DATA(HttpStatus.BAD_REQUEST, "COMMON-003", "유효하지 않은 데이터"),

    NOT_FOUND_LOCATION(HttpStatus.NOT_FOUND, "LOC-004", "존재하지 않는 위치 등록 정보"),
    NOT_FOUND_PIN(HttpStatus.NOT_FOUND, "LOC-009", "존재하지 않는 핀"),

    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "USER-002", "존재하지 않는 회원"),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;

    FailureResponse(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
