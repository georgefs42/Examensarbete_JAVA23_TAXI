package se.george.taxi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.george.taxi.models.MonthlyRapport;
import se.george.taxi.services.MonthlyRapportService;

import java.util.List;

@RestController
@RequestMapping("/monthly-rapport")
public class MonthlyRapportController {

    private final MonthlyRapportService monthlyRapportService;

    @Autowired
    public MonthlyRapportController(MonthlyRapportService monthlyRapportService) {
        this.monthlyRapportService = monthlyRapportService;
    }

    @PostMapping
    public MonthlyRapport createMonthlyRapport(@RequestBody MonthlyRapport monthlyRapport) {
        return monthlyRapportService.saveMonthlyRapport(monthlyRapport);
    }

    @GetMapping
    public List<MonthlyRapport> getAllMonthlyRapports() {
        return monthlyRapportService.getAllMonthlyRapports();
    }

    @GetMapping("/driver")
    public List<MonthlyRapport> getMonthlyRapportsByDriver(@RequestParam Long driverId, @RequestParam int year, @RequestParam int month) {
        return monthlyRapportService.getMonthlyRapportByDriver(driverId, year, month);
    }
}
