package se.george.taxi.controllers;

import se.george.taxi.models.DriverIncome;
import se.george.taxi.services.DriverIncomeService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/income")  // Root path for all endpoints in this controller
@CrossOrigin(origins = "http://localhost:8080") // To allow frontend to make requests
public class DriverIncomeController {

    private final DriverIncomeService service;

    // Constructor injection for the service
    public DriverIncomeController(DriverIncomeService service) {
        this.service = service;
    }

    @PostMapping  // Handles POST requests
    public DriverIncome addIncome(@RequestBody DriverIncome income) {
        return service.saveIncome(income);
    }

    @GetMapping("/monthly")  // Handles GET requests with query parameters
    public List<DriverIncome> getMonthlyData(@RequestParam int year, @RequestParam int month) {
        return service.getMonthlyData(year, month);
    }
}
