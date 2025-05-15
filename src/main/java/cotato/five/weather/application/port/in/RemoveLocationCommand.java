package cotato.five.weather.application.port.in;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public interface RemoveLocationCommand {
    void remove(Long id, UUID memberId);
}
