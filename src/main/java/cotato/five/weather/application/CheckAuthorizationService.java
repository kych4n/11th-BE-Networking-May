package cotato.five.weather.application;

import cotato.five.weather.adapter.in.dto.response.CheckAuthorizationResponse;
import cotato.five.weather.application.port.in.CheckAuthorizationQuery;
import cotato.five.weather.application.port.out.LoadMemberPort;
import cotato.five.weather.common.FailureResponse;
import cotato.five.weather.exception.NotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckAuthorizationService implements CheckAuthorizationQuery {
    private final LoadMemberPort loadMemberPort;

    @Override
    public CheckAuthorizationResponse check(UUID id) {
        return loadMemberPort.findById(id).map(member -> new CheckAuthorizationResponse(member.getUsername()))
                .orElseThrow(() -> new NotFoundException(FailureResponse.NOT_FOUND_USER));
    }
}
