package RockieProject.backend.Accessory.Infraestructure;

import RockieProject.backend.Accessory.Domain.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessoryRepository extends JpaRepository<Accessory, Long> {
    Accessory findByName(String name);

}
