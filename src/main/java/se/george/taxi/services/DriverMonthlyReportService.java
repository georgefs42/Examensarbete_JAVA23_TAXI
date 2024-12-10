package se.george.taxi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.george.taxi.models.Driver;
import se.george.taxi.models.DriverMonthlyReport;
import se.george.taxi.repositories.DriverIncomeRepository;
import se.george.taxi.repositories.DriverMonthlyReportRepository;
import se.george.taxi.repositories.DriverRepository;

import java.math.BigDecimal;  // Add missing import for BigDecimal
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DriverMonthlyReportService {

    @Autowired
    private DriverMonthlyReportRepository repository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DriverIncomeRepository driverIncomeRepository;

    // Get all rapports
    public List<DriverMonthlyReport> getAllRapports() {
        return repository.findAll();  // This will get all rapports from the database
    }

    public Optional<DriverMonthlyReport> getRapportById(Long driverId) {
        return repository.findById(driverId);
    }

    public DriverMonthlyReport createRapport(Long driverId, LocalDate periodFrom, LocalDate periodTo) {
        // Fetch driver details from the 'driver' table
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found for ID: " + driverId));

        // Calculate total profit from the 'driver_income' table
        Double totalProfit = driverIncomeRepository.calculateTotalProfit(driverId, periodFrom, periodTo);

        // Convert totalProfit (Double) to BigDecimal before saving
        BigDecimal totalProfitBigDecimal = BigDecimal.valueOf(totalProfit);

        // Create a new DriverMonthlyRapport entity
        DriverMonthlyReport rapport = new DriverMonthlyReport();
        rapport.setDriverId(driverId);
        rapport.setName(driver.getName());
        rapport.setPersonalNumber(driver.getPersonalNumber());
        rapport.setTotalProfit(totalProfitBigDecimal);  // Set BigDecimal value
        rapport.setPeriodFrom(periodFrom);
        rapport.setPeriodTo(periodTo);

        // Save and return the new rapport
        return repository.save(rapport);
    }

    public DriverMonthlyReport updateRapport(Long driverId, LocalDate periodFrom, LocalDate periodTo) {
        // Fetch the rapport and update it
        Optional<DriverMonthlyReport> existingRapport = repository.findById(driverId);
        if (existingRapport.isPresent()) {
            DriverMonthlyReport rapport = existingRapport.get();
            Double totalProfit = driverIncomeRepository.calculateTotalProfit(driverId, periodFrom, periodTo);

            // Convert totalProfit (Double) to BigDecimal before updating
            BigDecimal totalProfitBigDecimal = BigDecimal.valueOf(totalProfit);

            rapport.setTotalProfit(totalProfitBigDecimal);  // Set BigDecimal value
            rapport.setPeriodFrom(periodFrom);
            rapport.setPeriodTo(periodTo);

            return repository.save(rapport);
        }
        throw new RuntimeException("Rapport not found for driverId: " + driverId);
    }

    public void deleteRapport(Long driverId) {
        repository.deleteById(driverId);
    }
}