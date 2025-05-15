package cotato.five.weather.application.port.in;

import cotato.five.weather.adapter.in.dto.request.ModifyLocationRequest;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public interface ModifyLocationCommand {
    void modify(ModifyLocationRequest request, Long id, UUID memberId);
}
