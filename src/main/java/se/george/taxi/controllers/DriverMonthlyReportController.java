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
public class DriverMonthlyReportController {

    @Autowired
    private DriverMonthlyReportService service;

    /**
     * Get all monthly rapports.
     * @return List of DriverMonthlyRapportDTO
     */
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

    /**
     * Get a monthly rapport by driverId.
     * @param driverId Driver's unique ID.
     * @return DriverMonthlyRapportDTO
     */
    @GetMapping("/{driverId}")
    public ResponseEntity<DriverMonthlyReportDTO> getRapportById(@PathVariable Long driverId) {
        Optional<DriverMonthlyReport> rapport = service.getRapportById(driverId);
        if (rapport.isPresent()) {
            return new ResponseEntity<>(convertToDTO(rapport.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Create a new monthly rapport for a driver based on JSON input.
     * @param request DriverMonthlyRapportDTO object containing the details.
     * @return Created DriverMonthlyRapportDTO.
     */
    @PostMapping
    public ResponseEntity<DriverMonthlyReportDTO> createRapport(@RequestBody DriverMonthlyReportDTO request) {
        try {
            DriverMonthlyReport rapport = service.createRapport(
                    request.getDriverId(),
                    request.getPeriodFrom(),
                    request.getPeriodTo()
            );
            return new ResponseEntity<>(convertToDTO(rapport), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Invalid input
        }
    }

    /**
     * Update an existing monthly rapport for a driver.
     * @param driverId Driver's unique ID.
     * @param periodFrom Start date of the period.
     * @param periodTo End date of the period.
     * @return Updated DriverMonthlyRapportDTO.
     */
    @PutMapping("/{driverId}")
    public ResponseEntity<DriverMonthlyReportDTO> updateRapport(
            @PathVariable Long driverId,
            @RequestParam LocalDate periodFrom,
            @RequestParam LocalDate periodTo) {

        try {
            DriverMonthlyReport updatedRapport = service.updateRapport(driverId, periodFrom, periodTo);
            return new ResponseEntity<>(convertToDTO(updatedRapport), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // If the rapport is not found
        }
    }

    /**
     * Delete a monthly rapport for a driver.
     * @param driverId Driver's unique ID.
     * @return Status message.
     */
    @DeleteMapping("/{driverId}")
    public ResponseEntity<Void> deleteRapport(@PathVariable Long driverId) {
        try {
            service.deleteRapport(driverId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Successfully deleted
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // If the rapport is not found
        }
    }

    /**
     * Converts DriverMonthlyRapport to DriverMonthlyRapportDTO.
     * @param rapport DriverMonthlyRapport object.
     * @return DriverMonthlyRapportDTO
     */
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