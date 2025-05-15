package cotato.five.weather.application.port.in;

import cotato.five.weather.adapter.in.dto.response.LoadLocationDetailResponse;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadLocationDetailQuery {
    LoadLocationDetailResponse load(Long id, UUID memberId);
}
