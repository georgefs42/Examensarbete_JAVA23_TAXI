package se.george.taxi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.george.taxi.models.DriverMonthlyReport;
import se.george.taxi.models.SalaryReport;
import se.george.taxi.repositories.DriverMonthlyReportRepository;
import se.george.taxi.repositories.SalaryReportRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SalaryReportService {

    @Autowired
    private SalaryReportRepository salaryReportRepository;

    @Autowired
    private DriverMonthlyReportRepository driverMonthlyReportRepository;

    // Create salary report
    public SalaryReport createSalaryReport(Long driverId, int month, int year, BigDecimal tax) {
        // Fetch the DriverMonthlyReport based on driverId and the period (month & year)
        LocalDate periodFrom = LocalDate.of(year, month, 1);
        LocalDate periodTo = periodFrom.withDayOfMonth(periodFrom.lengthOfMonth()); // End of the month

        Optional<DriverMonthlyReport> driverReportOptional = driverMonthlyReportRepository
                .findByDriverIdAndPeriodFromAndPeriodTo(driverId, periodFrom, periodTo);

        if (driverReportOptional.isPresent()) {
            DriverMonthlyReport driverReport = driverReportOptional.get();

            // Calculate salary after tax
            BigDecimal salaryAfterTax = driverReport.getTotalProfit().subtract(tax);

            // Create a new SalaryReport instance
            SalaryReport salaryReport = new SalaryReport();
            salaryReport.setDriverId(driverId);
            salaryReport.setName(driverReport.getName());
            salaryReport.setPersonalNumber(driverReport.getPersonalNumber());
            salaryReport.setPeriodFrom(periodFrom);
            salaryReport.setPeriodTo(periodTo);
            salaryReport.setTax(tax);
            salaryReport.setSalaryAfterTax(salaryAfterTax);
            salaryReport.setTotalProfit(driverReport.getTotalProfit()); // Assuming the total profit comes from the driver report

            // Save and return the new salary report
            return salaryReportRepository.save(salaryReport);
        } else {
            throw new IllegalArgumentException("Driver report not found for the given period.");
        }
    }

    // Get all salary reports
    public List<SalaryReport> getAllSalaryReports() {
        return salaryReportRepository.findAll();
    }

    // Get salary report by id
    public Optional<SalaryReport> getSalaryReportById(Long id) {
        return salaryReportRepository.findById(id);
    }

    // Update salary report
    public SalaryReport updateSalaryReport(Long id, Long driverId, int month, int year, BigDecimal tax) {
        Optional<SalaryReport> existingReport = salaryReportRepository.findById(id);
        if (existingReport.isPresent()) {
            SalaryReport report = existingReport.get();
            // Fetch the updated driver report
            LocalDate periodFrom = LocalDate.of(year, month, 1);
            LocalDate periodTo = periodFrom.withDayOfMonth(periodFrom.lengthOfMonth());

            Optional<DriverMonthlyReport> driverReportOptional = driverMonthlyReportRepository
                    .findByDriverIdAndPeriodFromAndPeriodTo(driverId, periodFrom, periodTo);

            if (driverReportOptional.isPresent()) {
                DriverMonthlyReport driverReport = driverReportOptional.get();
                report.setDriverId(driverId);
                report.setName(driverReport.getName());
                report.setPersonalNumber(driverReport.getPersonalNumber());
                report.setPeriodFrom(periodFrom);
                report.setPeriodTo(periodTo);
                report.setTax(tax);
                report.setTotalProfit(driverReport.getTotalProfit());
                report.setSalaryAfterTax(driverReport.getTotalProfit().subtract(tax)); // Recalculate after tax
                return salaryReportRepository.save(report);
            } else {
                throw new IllegalArgumentException("Driver report not found for the given period.");
            }
        } else {
            throw new IllegalArgumentException("Salary report not found for ID: " + id);
        }
    }

    // Delete salary report
    public void deleteSalaryReport(Long id) {
        salaryReportRepository.deleteById(id);
    }
}
