package se.george.taxi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.george.taxi.models.Driver;
import se.george.taxi.models.DriverMonthlyReport;
import se.george.taxi.repositories.DriverIncomeRepository;
import se.george.taxi.repositories.DriverMonthlyReportRepository;
import se.george.taxi.repositories.DriverRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DriverMonthlyReportService {

    @Autowired
    private DriverMonthlyReportRepository reportRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DriverIncomeRepository incomeRepository;

    // Get all rapports
    public List<DriverMonthlyReport> getAllRapports() {
        return reportRepository.findAll();
    }

    // Get a rapport by driverId
    public Optional<DriverMonthlyReport> getRapportById(Long driverId) {
        return reportRepository.findById(driverId);
    }

    // Create a new rapport
    public DriverMonthlyReport createRapport(Long driverId, LocalDate periodFrom, LocalDate periodTo) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found"));

        Double totalProfit = incomeRepository.calculateTotalProfit(driverId, periodFrom, periodTo);
        BigDecimal totalProfitBigDecimal = BigDecimal.valueOf(totalProfit);

        DriverMonthlyReport rapport = new DriverMonthlyReport(
                driverId,
                driver.getName(),
                driver.getPersonalNumber(),
                totalProfitBigDecimal,
                periodFrom,
                periodTo
        );

        return reportRepository.save(rapport);
    }

    // Update a rapport
    public DriverMonthlyReport updateRapport(Long driverId, LocalDate periodFrom, LocalDate periodTo) {
        DriverMonthlyReport existingReport = reportRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        Double totalProfit = incomeRepository.calculateTotalProfit(driverId, periodFrom, periodTo);
        BigDecimal totalProfitBigDecimal = BigDecimal.valueOf(totalProfit);

        existingReport.setPeriodFrom(periodFrom);
        existingReport.setPeriodTo(periodTo);
        existingReport.setTotalProfit(totalProfitBigDecimal);

        return reportRepository.save(existingReport);
    }

    // Delete a rapport
    public void deleteRapport(Long driverId) {
        reportRepository.deleteById(driverId);
    }
}
