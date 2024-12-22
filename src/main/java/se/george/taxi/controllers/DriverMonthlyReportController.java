package se.george.taxi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.george.taxi.dto.DriverMonthlyReportDTO;
import se.george.taxi.models.DriverMonthlyReport;
import se.george.taxi.services.DriverMonthlyReportService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/monthly-rapport")
@CrossOrigin(origins = "https://taxi.georgedev.se")
public class DriverMonthlyReportController {

    @Autowired
    private DriverMonthlyReportService service;

    // Get all monthly rapports
    @GetMapping
    public ResponseEntity<List<DriverMonthlyReportDTO>> getAllRapports() {
        List<DriverMonthlyReport> rapports = service.getAllRapports();
        if (rapports.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<DriverMonthlyReportDTO> rapportDTOs = rapports.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(rapportDTOs, HttpStatus.OK);
    }

    // Get a monthly rapport by driverId
    @GetMapping("/{driverId}")
    public ResponseEntity<DriverMonthlyReportDTO> getRapportById(@PathVariable Long driverId) {
        Optional<DriverMonthlyReport> rapport = service.getRapportById(driverId);
        return rapport.map(value -> new ResponseEntity<>(convertToDTO(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Create a new monthly rapport
    @PostMapping
    public ResponseEntity<DriverMonthlyReport> createMonthlyReport(@RequestBody DriverMonthlyReport request) {
        try {
            DriverMonthlyReport report = service.createRapport(request.getDriverId(), request.getPeriodFrom(), request.getPeriodTo());
            return new ResponseEntity<>(report, HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the exception details for debugging
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Update a monthly rapport
    @PutMapping("/{driverId}")
    public ResponseEntity<DriverMonthlyReportDTO> updateRapport(
            @PathVariable Long driverId,
            @RequestParam LocalDate periodFrom,
            @RequestParam LocalDate periodTo) {
        try {
            DriverMonthlyReport updatedRapport = service.updateRapport(driverId, periodFrom, periodTo);
            return new ResponseEntity<>(convertToDTO(updatedRapport), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a monthly rapport
    @DeleteMapping("/{driverId}")
    public ResponseEntity<Void> deleteRapport(@PathVariable Long driverId) {
        try {
            service.deleteRapport(driverId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Convert entity to DTO
    private DriverMonthlyReportDTO convertToDTO(DriverMonthlyReport rapport) {
        return DriverMonthlyReportDTO.builder()
                .driverId(rapport.getDriverId())
                .name(rapport.getName())
                .personalNumber(rapport.getPersonalNumber())
                .totalProfit(rapport.getTotalProfit())
                .periodFrom(rapport.getPeriodFrom())
                .periodTo(rapport.getPeriodTo())
                .build();
    }
}
