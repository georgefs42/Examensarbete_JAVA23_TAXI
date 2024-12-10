package se.george.taxi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.george.taxi.dto.DriverMonthlyRapportDTO;
import se.george.taxi.models.DriverMonthlyRapport;
import se.george.taxi.services.DriverMonthlyRapportService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/monthly-rapport")
public class DriverMonthlyRapportController {

    @Autowired
    private DriverMonthlyRapportService service;

    /**
     * Get all monthly rapports.
     * @return List of DriverMonthlyRapportDTO
     */
    @GetMapping
    public ResponseEntity<List<DriverMonthlyRapportDTO>> getAllRapports() {
        List<DriverMonthlyRapport> rapports = service.getAllRapports();
        if (rapports.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<DriverMonthlyRapportDTO> rapportDTOs = rapports.stream()
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
    public ResponseEntity<DriverMonthlyRapportDTO> getRapportById(@PathVariable Long driverId) {
        Optional<DriverMonthlyRapport> rapport = service.getRapportById(driverId);
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
    public ResponseEntity<DriverMonthlyRapportDTO> createRapport(@RequestBody DriverMonthlyRapportDTO request) {
        try {
            DriverMonthlyRapport rapport = service.createRapport(
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
    public ResponseEntity<DriverMonthlyRapportDTO> updateRapport(
            @PathVariable Long driverId,
            @RequestParam LocalDate periodFrom,
            @RequestParam LocalDate periodTo) {

        try {
            DriverMonthlyRapport updatedRapport = service.updateRapport(driverId, periodFrom, periodTo);
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
    private DriverMonthlyRapportDTO convertToDTO(DriverMonthlyRapport rapport) {
        return DriverMonthlyRapportDTO.builder()
                .driverId(rapport.getDriverId())
                .name(rapport.getName())
                .personalNumber(rapport.getPersonalNumber())
                .totalProfit(rapport.getTotalProfit())
                .periodFrom(rapport.getPeriodFrom())
                .periodTo(rapport.getPeriodTo())
                .build();
    }
}
