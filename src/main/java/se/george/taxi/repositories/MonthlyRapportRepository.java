package se.george.taxi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.george.taxi.models.MonthlyRapport;

public interface MonthlyRapportRepository extends JpaRepository<MonthlyRapport, Long> {
    // Custom queries can be added here
}
