package se.george.taxi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.george.taxi.models.DriverMonthlyReport;

import java.time.LocalDate;
import java.util.Optional;

public interface DriverMonthlyReportRepository extends JpaRepository<DriverMonthlyReport, Long> {
    Optional<DriverMonthlyReport> findByDriverIdAndPeriodFromAndPeriodTo(Long driverId, LocalDate periodFrom, LocalDate periodTo);
}
