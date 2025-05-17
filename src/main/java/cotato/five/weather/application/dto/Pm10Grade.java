package cotato.five.weather.application.dto;

import org.apache.logging.log4j.util.Strings;

public enum Pm10Grade {
    GOOD("좋음",0,30),
    NORMAL("보통",31,75),
    BAD("나쁨",76,150),
    VERY_BAD("매우 나쁨",151,Integer.MAX_VALUE)
    ;

    private String Pm10Level;
    private int min;
    private int max;

    Pm10Grade(String Pm10Level, int min, int max) {
        this.Pm10Level = Pm10Level;
        this.min = min;
        this.max = max;
    }

    public static String getPm10Grade(double num) {
        for(Pm10Grade p : Pm10Grade.values()) {
            if(num >= p.min && num <= p.max) {
                return p.Pm10Level;
            }
        }
        return "알 수 없음";
    }
}
