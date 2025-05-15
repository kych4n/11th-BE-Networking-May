package cotato.five.weather.application.port.out;

import cotato.five.weather.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginPort extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
}
