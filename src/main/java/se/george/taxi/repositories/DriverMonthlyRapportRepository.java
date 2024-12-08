package se.george.taxi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.george.taxi.models.DriverMonthlyRapport;

public interface DriverMonthlyRapportRepository extends JpaRepository<DriverMonthlyRapport, Long> {
    // No additional methods are required if you are using JPA's built-in findAll() and findById()
}
