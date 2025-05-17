package cotato.five.weather.application.dto;

public enum UviGrade {
    LOW("낮음",0,2),
    NORMAL("보통",3,5),
    HIGH("높음",6,7),
    VERY_HIGH("매우 높음",8,10),
    DANGER("위험",11,Integer.MAX_VALUE)
    ;

    private String level;
    private int min;
    private int max;

    UviGrade(String level, int min, int max) {
        this.level = level;
        this.min = min;
        this.max = max;
    }

    public static String getUviGrade(double num) {
        for (UviGrade u : UviGrade.values()) {
            if (num >= u.min && num <= u.max) {
                return u.level;
            }
        }
        return "알 수 없음";
    }
}
