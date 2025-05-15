package cotato.five.weather.application.port.in;

import cotato.five.weather.adapter.in.dto.response.LoadLocationListResponse;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public interface LoadLocationListQuery {
    LoadLocationListResponse load(UUID id);
}
