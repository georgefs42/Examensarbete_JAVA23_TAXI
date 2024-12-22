package se.george.taxi.controllers;

import org.springframework.web.bind.annotation.*;
import se.george.taxi.models.DriverIncome;
import se.george.taxi.services.DriverIncomeService;

import java.util.List;

@RestController
@RequestMapping("/driver-income")
@CrossOrigin(origins = "https://taxi.georgedev.se")
public class DriverIncomeController {

    private final DriverIncomeService driverIncomeService;

    // Constructor injection for the service
    public DriverIncomeController(DriverIncomeService driverIncomeService) {
        this.driverIncomeService = driverIncomeService;
    }

    // Get all driver incomes
    @GetMapping
    public List<DriverIncome> getAllIncomes() {
        return driverIncomeService.getAllIncomes();
    }

    // Create new income
    @PostMapping
    public DriverIncome addIncome(@RequestBody DriverIncome income) {
        return driverIncomeService.saveIncome(income);
    }

    // Get monthly income data
    @GetMapping("/monthly")
    public List<DriverIncome> getMonthlyData(@RequestParam int year, @RequestParam int month) {
        return driverIncomeService.getMonthlyData(year, month);
    }

    // Update an existing income
    @PutMapping("/{id}")
    public DriverIncome updateIncome(@PathVariable Long id, @RequestBody DriverIncome updatedIncome) {
        return driverIncomeService.updateIncome(id, updatedIncome);
    }

    // Delete income
    @DeleteMapping("/{id}")
    public void deleteIncome(@PathVariable Long id) {
        driverIncomeService.deleteIncome(id);
    }
}
