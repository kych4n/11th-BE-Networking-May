package cotato.five.weather.application.port.in;

import cotato.five.weather.adapter.in.dto.request.LoginRequest;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public interface LoginCommand {
    UUID login(LoginRequest request);
}
