package RockieProject.backend.Rockie.Infraestructure;

import RockieProject.backend.Rockie.Domain.Rockie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RockieRepository extends JpaRepository<Rockie, Long> {
}
