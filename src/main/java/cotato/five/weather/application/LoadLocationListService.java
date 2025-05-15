package cotato.five.weather.application;

import cotato.five.weather.adapter.in.dto.response.LoadLocationListResponse;
import cotato.five.weather.adapter.in.dto.response.LoadLocationResponse;
import cotato.five.weather.application.port.in.LoadLocationListQuery;
import cotato.five.weather.application.port.out.LoadLocationListPort;
import cotato.five.weather.application.port.out.LoadMemberPort;
import cotato.five.weather.common.FailureResponse;
import cotato.five.weather.domain.Member;
import cotato.five.weather.exception.NotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoadLocationListService implements LoadLocationListQuery {
    private final LoadLocationListPort loadLocationListPort;
    private final LoadMemberPort loadMemberPort;

    @Override
    public LoadLocationListResponse load(UUID id) {
        Member member = loadMemberPort.findById(id)
                .orElseThrow(() -> new NotFoundException(FailureResponse.NOT_FOUND_USER));
        return new LoadLocationListResponse(loadLocationListPort.findAllByMember(member).stream()
                .map(location -> new LoadLocationResponse(location.getId(), location.getName())).toList());
    }
}
