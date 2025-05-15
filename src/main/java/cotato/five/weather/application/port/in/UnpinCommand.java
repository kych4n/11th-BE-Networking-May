package cotato.five.weather.application.port.in;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public interface UnpinCommand {
    void unpin(Long id, UUID memberId);
}
