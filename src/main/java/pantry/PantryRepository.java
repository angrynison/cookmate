package pantry;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PantryRepository extends JpaRepository<Pantry, Long> {

    Pantry findByName(String name);
}
