package cotato.five.weather.application.dto;

import java.util.List;

public record AirPollutionResponse(
        List<AirPollutionData> list
) {
    public record AirPollutionData(
            Components components
    ) {}

    public record Components(
            double pm10,
            double pm2_5
    ) {}
}