package cotato.five.weather.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessResponse {
    REGISTERED_LOCATION(HttpStatus.CREATED, "LOC-001", "위치 등록 성공"),
    RETRIEVED_LIST_LOCATION(HttpStatus.OK, "LOC-002", "위치 목록 조회 성공"),
    RETRIEVED_DETAIL_LOCATION(HttpStatus.OK, "LOC-003", "위치 상세 조회 성공"),
    DELETED_LOCATION(HttpStatus.OK, "LOC-005", "위치 삭제 성공"),
    MODIFIED_LOCATION(HttpStatus.OK, "LOC-006", "위치 수정 성공"),
    REGISTERED_PIN(HttpStatus.CREATED, "LOC-007", "위치 핀 등록 성공"),
    DELETED_PIN(HttpStatus.OK, "LOC-008", "위치 핀 삭제 성공"),

    LOGGED_IN(HttpStatus.OK, "USER-001", "로그인 성공"),
    LOGGED_OUT(HttpStatus.OK, "USER-003", "로그아웃 성공"),

    RETRIEVED_DETAIL_DAILY_WEATHER(HttpStatus.OK, "WEA-001", "날짜별 날씨 상세 조회 성공"),
    RETRIEVED_HOURLY_WEATHER(HttpStatus.OK, "WEA-002", "시간별 날씨 조회 성공"),
    RETRIEVED_WEEKLY_WEATHER(HttpStatus.OK, "WEA-003", "5일 주간 예보 조회 성공"),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;

    SuccessResponse(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
