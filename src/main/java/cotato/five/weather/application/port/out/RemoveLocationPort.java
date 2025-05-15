package cotato.five.weather.application.port.out;

import cotato.five.weather.domain.Location;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemoveLocationPort extends JpaRepository<Location, Long> {
    Optional<Location> findByIdAndMember_Id(Long id, UUID memberId);
}
