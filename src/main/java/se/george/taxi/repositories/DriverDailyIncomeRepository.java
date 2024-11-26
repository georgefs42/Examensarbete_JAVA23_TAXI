package se.george.taxi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.george.taxi.models.DriverDailyIncome;

public interface DriverDailyIncomeRepository extends JpaRepository<DriverDailyIncome, Long> {
}
