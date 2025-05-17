package cotato.five.weather.application;

import cotato.five.weather.application.dto.AirPollutionResponse;
import cotato.five.weather.exception.BadRequestException;
import cotato.five.weather.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static cotato.five.weather.common.FailureResponse.EXTERNAL_SERVER_ERROR;
import static cotato.five.weather.common.FailureResponse.INTERNAL_SEVER_ERROR;

@Service
@RequiredArgsConstructor
public class AirPollutionService {

    private final RestTemplate restTemplate;
    @Value("${openweather.api.key}")
    private String API_KEY;

    public AirPollutionResponse.Components getComponents(double lat, double lon) {
        try {
            String url = UriComponentsBuilder
                    .fromHttpUrl("https://api.openweathermap.org/data/2.5/air_pollution")
                    .queryParam("lat", lat)
                    .queryParam("lon", lon)
                    .queryParam("appid", API_KEY)
                    .toUriString();

            AirPollutionResponse response = restTemplate.getForObject(url, AirPollutionResponse.class);
            return response.list().get(0).components();
        } catch (HttpClientErrorException | ResourceAccessException e) {
            throw new UnauthorizedException(EXTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new BadRequestException(INTERNAL_SEVER_ERROR);
        }
    }
}

