package cotato.five.weather.application;

import cotato.five.weather.adapter.in.dto.request.LoginRequest;
import cotato.five.weather.application.port.in.LoginCommand;
import cotato.five.weather.application.port.out.LoginPort;
import cotato.five.weather.common.FailureResponse;
import cotato.five.weather.domain.Member;
import cotato.five.weather.exception.NotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginCommand {
    private final LoginPort loginPort;

    @Override
    @Transactional
    public UUID login(LoginRequest request) {
        return loginPort.findByUsername(request.username())
                .map(member -> {
                    if (member.getPassword().equals(request.password())) {
                        return member.getId();
                    } else {
                        throw new NotFoundException(FailureResponse.NOT_FOUND_USER);
                    }
                })
                .orElseGet(() -> {
                    Member newMember = new Member(request.username(), request.password());
                    loginPort.save(newMember);
                    return newMember.getId();
                });
    }
}
