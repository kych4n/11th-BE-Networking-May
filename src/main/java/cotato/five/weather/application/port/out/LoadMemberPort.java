package cotato.five.weather.application.port.out;

import cotato.five.weather.domain.Member;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadMemberPort extends JpaRepository<Member, UUID> {
}
