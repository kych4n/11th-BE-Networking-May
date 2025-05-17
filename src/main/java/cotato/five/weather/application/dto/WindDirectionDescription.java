package cotato.five.weather.application.dto;

public enum WindDirectionDescription {
    N("북풍", 337.5, 22.5),
    NE("북동풍", 22.5, 67.5),
    E("동풍", 67.5, 112.5),
    SE("남동풍", 112.5, 157.5),
    S("남풍", 157.5, 202.5),
    SW("남서풍", 202.5, 247.5),
    W("서풍", 247.5, 292.5),
    NW("북서풍", 292.5, 337.5);

    private final String windDirection;
    private final double min;
    private final double max;

    WindDirectionDescription(String windDirection, double min, double max) {
        this.windDirection = windDirection;
        this.min = min;
        this.max = max;
    }


    public static String getWindDirection(double deg) {
        deg = deg % 360;
        for (WindDirectionDescription d : values()) {
            if (d.min > d.max) {
                if (deg >= d.min || deg < d.max) return d.windDirection;
            } else {
                if (deg >= d.min && deg < d.max) return d.windDirection;
            }
        }
        return "알 수 없음";
    }
}

