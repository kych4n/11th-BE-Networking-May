package cotato.five.weather.application;

import cotato.five.weather.application.port.in.PinCommand;
import cotato.five.weather.application.port.out.PinPort;
import cotato.five.weather.common.FailureResponse;
import cotato.five.weather.domain.Location;
import cotato.five.weather.exception.UnauthorizedException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PinService implements PinCommand {
    private final PinPort pinPort;

    @Override
    @Transactional
    public void pin(Long locationId, UUID memberId) {
        Location location = pinPort.findByIdAndMember_Id(locationId, memberId)
                .orElseThrow(() -> new UnauthorizedException(FailureResponse.UNAUTHORIZED_LOCATION));
        location.getPin().change(true);
    }
}
