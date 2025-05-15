package cotato.five.weather.application.port;

import cotato.five.weather.adapter.in.dto.LoginRequest;
import cotato.five.weather.application.port.in.LoginCommand;
import cotato.five.weather.application.port.out.LoginRepository;
import cotato.five.weather.common.FailureResponse;
import cotato.five.weather.domain.Member;
import cotato.five.weather.exception.NotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginCommand {
    private final LoginRepository loginRepository;

    @Override
    public UUID login(LoginRequest request) {
        return loginRepository.findByUsername(request.username())
                .map(member -> {
                    if (member.getPassword().equals(request.password())) {
                        return member.getId();
                    } else {
                        throw new NotFoundException(FailureResponse.NOT_FOUND_USER);
                    }
                })
                .orElseGet(() -> {
                    Member newMember = new Member(request.username(), request.password());
                    loginRepository.save(newMember);
                    return newMember.getId();
                });
    }
}
