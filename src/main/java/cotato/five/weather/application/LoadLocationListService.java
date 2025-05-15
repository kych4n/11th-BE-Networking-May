package cotato.five.weather.application;

import cotato.five.weather.adapter.in.dto.response.LoadLocationListResponse;
import cotato.five.weather.adapter.in.dto.response.LoadLocationResponse;
import cotato.five.weather.application.port.in.LoadLocationListQuery;
import cotato.five.weather.application.port.out.LoadLocationListPort;
import cotato.five.weather.application.port.out.LoadMemberPort;
import cotato.five.weather.common.FailureResponse;
import cotato.five.weather.domain.Location;
import cotato.five.weather.domain.Member;
import cotato.five.weather.exception.NotFoundException;
import java.util.Comparator;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoadLocationListService implements LoadLocationListQuery {
    private final LoadLocationListPort loadLocationListPort;
    private final LoadMemberPort loadMemberPort;

    @Override
    @Transactional(readOnly = true)
    public LoadLocationListResponse load(UUID id) {
        Member member = loadMemberPort.findById(id)
                .orElseThrow(() -> new NotFoundException(FailureResponse.NOT_FOUND_USER));
        return new LoadLocationListResponse(loadLocationListPort.findAllByMember(member).stream()
                .sorted(
                        Comparator.comparing((Location location) -> location.getPin().getIsPinned(),
                                        Comparator.nullsLast(Comparator.reverseOrder()))
                                .thenComparing(location -> location.getPin().getPinnedAt(),
                                        Comparator.nullsLast(Comparator.reverseOrder()))
                )
                .map(location -> new LoadLocationResponse(location.getId(), location.getName())).toList());
    }
}
