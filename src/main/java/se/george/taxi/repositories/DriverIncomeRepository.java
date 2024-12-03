package se.george.taxi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.george.taxi.models.DriverIncome;

import java.time.LocalDate;
import java.util.List;

public interface DriverIncomeRepository extends JpaRepository<DriverIncome, Long> {

    // Custom query to find all incomes for a given month and year
    List<DriverIncome> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
