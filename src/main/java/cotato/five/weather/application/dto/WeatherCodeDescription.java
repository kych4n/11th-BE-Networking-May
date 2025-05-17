package cotato.five.weather.application.dto;

public enum WeatherCodeDescription {
    // 날씨 코드 설명 - enum 매핑 클래스
    Thunderstorm("천둥번개"),
    Drizzle("이슬비"),
    Rain("비"),
    Snow("눈"),
    Atmosphere("안개"),
    Clear("맑음"),
    Clouds("흐림")
    ;

    private String weather;


    private WeatherCodeDescription(String weather) {
        this.weather = weather;
    }


    public static String change(String weather) {
        for(WeatherCodeDescription w :WeatherCodeDescription.values()) {
            if(w.name().equals(weather))
                return w.weather;
        }
        return "알 수 없음";
    }
}
