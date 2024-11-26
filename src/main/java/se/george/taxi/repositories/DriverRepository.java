package se.george.taxi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.george.taxi.models.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long> {
}
