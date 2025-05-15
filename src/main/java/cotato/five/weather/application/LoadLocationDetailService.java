package cotato.five.weather.application;

import cotato.five.weather.adapter.in.dto.response.LoadLocationDetailResponse;
import cotato.five.weather.application.port.in.LoadLocationDetailQuery;
import cotato.five.weather.application.port.out.LoadLocationDetailPort;
import cotato.five.weather.common.FailureResponse;
import cotato.five.weather.domain.Location;
import cotato.five.weather.exception.UnauthorizedException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoadLocationDetailService implements LoadLocationDetailQuery {
    private final LoadLocationDetailPort loadLocationDetailPort;

    @Override
    @Transactional(readOnly = true)
    public LoadLocationDetailResponse load(Long id, UUID memberId) {
        Location location = loadLocationDetailPort.findByIdAndMember_Id(id, memberId)
                .orElseThrow(() -> new UnauthorizedException(FailureResponse.UNAUTHORIZED_LOCATION));
        return new LoadLocationDetailResponse(location.getId(), location.getName(), location.getLatitude(),
                location.getLongitude());
    }
}
