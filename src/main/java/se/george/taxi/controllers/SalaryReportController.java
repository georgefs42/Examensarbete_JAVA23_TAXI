package se.george.taxi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.george.taxi.dto.SalaryReportDTO;
import se.george.taxi.models.SalaryReport;
import se.george.taxi.services.SalaryReportService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/salary-report")
public class SalaryReportController {

    @Autowired
    private SalaryReportService salaryReportService;

    // Create salary report
    @PostMapping
    public ResponseEntity<SalaryReport> createSalaryReport(@RequestBody SalaryReportDTO dto) {
        try {
            // Call the service to create a new SalaryReport
            SalaryReport salaryReport = salaryReportService.createSalaryReport(
                    dto.getDriverId(),
                    dto.getMonth(),
                    dto.getYear(),
                    dto.getTax()
            );

            // Return the created SalaryReport in the response
            return ResponseEntity.ok(salaryReport);
        } catch (IllegalArgumentException e) {
            // Return 400 Bad Request if the period or driver details are invalid
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Get all salary reports
    @GetMapping
    public ResponseEntity<List<SalaryReport>> getAllSalaryReports() {
        return ResponseEntity.ok(salaryReportService.getAllSalaryReports());
    }

    // Get salary report by ID
    @GetMapping("/{id}")
    public ResponseEntity<SalaryReport> getSalaryReportById(@PathVariable Long id) {
        return salaryReportService.getSalaryReportById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update salary report
    @PutMapping("/{id}")
    public ResponseEntity<SalaryReport> updateSalaryReport(@PathVariable Long id, @RequestBody SalaryReportDTO dto) {
        try {
            SalaryReport updatedReport = salaryReportService.updateSalaryReport(
                    id, dto.getDriverId(), dto.getMonth(), dto.getYear(), dto.getTax()
            );
            return ResponseEntity.ok(updatedReport);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Delete salary report
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalaryReport(@PathVariable Long id) {
        salaryReportService.deleteSalaryReport(id);
        return ResponseEntity.noContent().build();
    }
}
