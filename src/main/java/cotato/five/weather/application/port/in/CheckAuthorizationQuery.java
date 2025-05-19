package cotato.five.weather.application.port.in;

import cotato.five.weather.adapter.in.dto.response.CheckAuthorizationResponse;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public interface CheckAuthorizationQuery {
    CheckAuthorizationResponse check(UUID id);
}
