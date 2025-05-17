package cotato.five.weather.application.dto;

public enum Pm2_5Grade {
    GOOD("좋음",0,15),
    NORMAL("보통",16,35),
    BAD("나쁨",36,75),
    VERY_BAD("매우 나쁨",76,Integer.MAX_VALUE)
    ;

    private String Pm2_5Level;
    private int min;
    private int max;

    Pm2_5Grade(String Pm2_5Level, int min, int max) {
        this.Pm2_5Level = Pm2_5Level;
        this.min = min;
        this.max = max;
    }

    public static String getPm2_5Grade(double num) {
        for(Pm2_5Grade p : Pm2_5Grade.values()) {
            if(num >= p.min && num <= p.max) {
                return p.Pm2_5Level;
            }
        }
        return "알 수 없음";
    }
}
