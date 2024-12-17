package se.george.taxi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.george.taxi.models.SalaryReport;

import java.time.LocalDate;
import java.util.List;

public interface SalaryReportRepository extends JpaRepository<SalaryReport, Long> {
    List<SalaryReport> findByDriverIdAndPeriodFromAndPeriodTo(Long driverId, LocalDate periodFrom, LocalDate periodTo);
}
