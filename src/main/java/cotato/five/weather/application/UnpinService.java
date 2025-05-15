package cotato.five.weather.application;

import cotato.five.weather.application.port.in.UnpinCommand;
import cotato.five.weather.application.port.out.UnpinPort;
import cotato.five.weather.common.FailureResponse;
import cotato.five.weather.domain.Location;
import cotato.five.weather.exception.UnauthorizedException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UnpinService implements UnpinCommand {
    private final UnpinPort unpinPort;

    @Override
    @Transactional
    public void unpin(Long id, UUID memberId) {
        Location location = unpinPort.findByIdAndMember_Id(id, memberId)
                .orElseThrow(() -> new UnauthorizedException(FailureResponse.UNAUTHORIZED_LOCATION));
        location.getPin().change(false);
    }
}
