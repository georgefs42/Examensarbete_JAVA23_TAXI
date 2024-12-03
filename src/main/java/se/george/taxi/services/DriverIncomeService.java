package se.george.taxi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.george.taxi.models.DriverIncome;
import se.george.taxi.repositories.DriverIncomeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DriverIncomeService {

    private final DriverIncomeRepository driverIncomeRepository;

    @Autowired
    public DriverIncomeService(DriverIncomeRepository driverIncomeRepository) {
        this.driverIncomeRepository = driverIncomeRepository;
    }

    // Method to save new income
    public DriverIncome saveIncome(DriverIncome income) {
        income.calculateDailyIncomeAfterVATAndProfit();  // Ensure profit is calculated
        return driverIncomeRepository.save(income);
    }

    // Method to get all incomes
    public List<DriverIncome> getAllIncomes() {
        return driverIncomeRepository.findAll();
    }

    // Method to get monthly data
    public List<DriverIncome> getMonthlyData(int year, int month) {
        // You can customize the query here for fetching monthly data
        return driverIncomeRepository.findAll(); // Placeholder
    }

    // Method to update an existing income
    public DriverIncome updateIncome(Long id, DriverIncome updatedIncome) {
        Optional<DriverIncome> existingIncomeOpt = driverIncomeRepository.findById(id);

        if (existingIncomeOpt.isPresent()) {
            DriverIncome existingIncome = existingIncomeOpt.get();

            // Update fields from the incoming data
            existingIncome.setDate(updatedIncome.getDate());
            existingIncome.setDailyIncome(updatedIncome.getDailyIncome());
            existingIncome.setDriver(updatedIncome.getDriver());

            // Recalculate VAT and profit
            existingIncome.calculateDailyIncomeAfterVATAndProfit();

            // Save updated entity
            return driverIncomeRepository.save(existingIncome);
        } else {
            throw new RuntimeException("DriverIncome not found with ID: " + id);
        }
    }

    // Method to delete an income by ID
    public void deleteIncome(Long id) {
        if (driverIncomeRepository.existsById(id)) {
            driverIncomeRepository.deleteById(id);
        } else {
            throw new RuntimeException("DriverIncome not found with ID: " + id);
        }
    }
}
