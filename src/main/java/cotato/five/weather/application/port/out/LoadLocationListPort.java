package cotato.five.weather.application.port.out;

import cotato.five.weather.domain.Location;
import cotato.five.weather.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadLocationListPort extends JpaRepository<Location, Long> {
    List<Location> findAllByMember(Member member);
}
