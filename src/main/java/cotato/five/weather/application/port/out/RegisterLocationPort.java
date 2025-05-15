package cotato.five.weather.application.port.out;

import cotato.five.weather.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterLocationPort extends JpaRepository<Location, Long> {
}
