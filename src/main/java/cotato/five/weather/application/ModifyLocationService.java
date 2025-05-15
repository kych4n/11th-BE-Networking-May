package cotato.five.weather.application;

import cotato.five.weather.adapter.in.dto.request.ModifyLocationRequest;
import cotato.five.weather.application.port.in.ModifyLocationCommand;
import cotato.five.weather.application.port.out.ModifyLocationPort;
import cotato.five.weather.common.FailureResponse;
import cotato.five.weather.domain.Location;
import cotato.five.weather.exception.UnauthorizedException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ModifyLocationService implements ModifyLocationCommand {
    private final ModifyLocationPort modifyLocationPort;

    @Override
    @Transactional
    public void modify(ModifyLocationRequest request, Long id, UUID memberId) {
        Location location = modifyLocationPort.findByIdAndMember_Id(id, memberId)
                .orElseThrow(() -> new UnauthorizedException(FailureResponse.UNAUTHORIZED_LOCATION));
        location.change(request.name(), request.latitude(), request.longitude());
    }
}
