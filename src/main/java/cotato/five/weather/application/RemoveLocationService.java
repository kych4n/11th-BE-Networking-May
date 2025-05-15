package cotato.five.weather.application;

import cotato.five.weather.application.port.in.RemoveLocationCommand;
import cotato.five.weather.application.port.out.RemoveLocationPort;
import cotato.five.weather.common.FailureResponse;
import cotato.five.weather.domain.Location;
import cotato.five.weather.exception.UnauthorizedException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveLocationService implements RemoveLocationCommand {
    private final RemoveLocationPort removeLocationPort;

    @Override
    public void remove(Long id, UUID memberId) {
        Location location = removeLocationPort.findByIdAndMember_Id(id, memberId)
                .orElseThrow(() -> new UnauthorizedException(
                        FailureResponse.UNAUTHORIZED_LOCATION));
        removeLocationPort.delete(location);
    }
}
