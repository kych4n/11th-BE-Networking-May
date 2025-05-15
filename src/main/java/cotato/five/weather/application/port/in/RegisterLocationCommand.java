package cotato.five.weather.application.port.in;

import cotato.five.weather.adapter.in.dto.request.RegisterLocationRequest;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public interface RegisterLocationCommand {
    void register(RegisterLocationRequest request, UUID id);
}
