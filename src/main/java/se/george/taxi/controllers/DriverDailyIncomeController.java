package se.george.taxi.controllers;

import org.springframework.web.bind.annotation.*;
import se.george.taxi.models.DriverDailyIncome;
import se.george.taxi.services.DriverDailyIncomeService;

import java.util.List;

@RestController
@RequestMapping("/daily-income")
public class DriverDailyIncomeController {
    private final DriverDailyIncomeService dailyIncomeService;

    public DriverDailyIncomeController(DriverDailyIncomeService dailyIncomeService) {
        this.dailyIncomeService = dailyIncomeService;
    }

    @GetMapping
    public List<DriverDailyIncome> getAllDailyIncomes() {
        return dailyIncomeService.getAllDailyIncomes();
    }

    @PostMapping
    public DriverDailyIncome saveDailyIncome(@RequestBody DriverDailyIncome dailyIncome) {
        return dailyIncomeService.saveDailyIncome(dailyIncome);
    }
}
