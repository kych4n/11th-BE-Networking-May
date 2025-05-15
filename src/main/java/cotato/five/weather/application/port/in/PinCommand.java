package cotato.five.weather.application.port.in;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public interface PinCommand {
    void pin(Long id, UUID memberId);
}
