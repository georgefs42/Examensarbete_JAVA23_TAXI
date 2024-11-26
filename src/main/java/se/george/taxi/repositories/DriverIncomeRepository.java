package se.george.taxi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.george.taxi.models.DriverIncome;

public interface DriverIncomeRepository extends JpaRepository<DriverIncome, Long> {
    // You can add custom queries here if needed, for example:
    // List<DriverIncome> findByDateBetween(LocalDate start, LocalDate end);
}
