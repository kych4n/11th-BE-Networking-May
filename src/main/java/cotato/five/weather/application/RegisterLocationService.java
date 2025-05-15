package cotato.five.weather.application;

import cotato.five.weather.adapter.in.dto.request.RegisterLocationRequest;
import cotato.five.weather.application.port.in.RegisterLocationCommand;
import cotato.five.weather.application.port.out.LoadMemberPort;
import cotato.five.weather.application.port.out.RegisterLocationPort;
import cotato.five.weather.common.FailureResponse;
import cotato.five.weather.domain.Location;
import cotato.five.weather.exception.NotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterLocationService implements RegisterLocationCommand {
    private final RegisterLocationPort registerLocationPort;
    private final LoadMemberPort loadMemberPort;

    @Override
    public void register(RegisterLocationRequest request, UUID id) {
        registerLocationPort.save(new Location(request.name(), request.latitude(), request.longitude(),
                loadMemberPort.findById(id).orElseThrow(() -> new NotFoundException(FailureResponse.NOT_FOUND_USER))));
    }
}
