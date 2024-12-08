package se.george.taxi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import se.george.taxi.models.DriverMonthlyRapport;
import se.george.taxi.services.DriverMonthlyRapportService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/monthly-rapport")
public class DriverMonthlyRapportController {

    @Autowired
    private DriverMonthlyRapportService service;

    /**
     * Get all monthly rapports.
     * @return List of DriverMonthlyRapport
     */
    @GetMapping
    public ResponseEntity<List<DriverMonthlyRapport>> getAllRapports() {
        List<DriverMonthlyRapport> rapports = service.getAllRapports();
        if (rapports.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(rapports, HttpStatus.OK);
    }

    /**
     * Get a monthly rapport by driverId.
     * @param driverId Driver's unique ID.
     * @return DriverMonthlyRapport
     */
    @GetMapping("/{driverId}")
    public ResponseEntity<DriverMonthlyRapport> getRapportById(@PathVariable Long driverId) {
        Optional<DriverMonthlyRapport> rapport = service.getRapportById(driverId);
        if (rapport.isPresent()) {
            return new ResponseEntity<>(rapport.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Create a new monthly rapport for a driver based on driverId and date range.
     * @param driverId Driver's unique ID.
     * @param periodFrom Start date of the period.
     * @param periodTo End date of the period.
     * @return Created DriverMonthlyRapport.
     */
    @PostMapping
    public ResponseEntity<DriverMonthlyRapport> createRapport(
            @RequestParam Long driverId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodTo) {

        try {
            DriverMonthlyRapport rapport = service.createRapport(driverId, periodFrom, periodTo);
            return new ResponseEntity<>(rapport, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Invalid driverId or other issues
        }
    }

    /**
     * Update an existing monthly rapport for a driver.
     * @param driverId Driver's unique ID.
     * @param periodFrom Start date of the period.
     * @param periodTo End date of the period.
     * @return Updated DriverMonthlyRapport.
     */
    @PutMapping("/{driverId}")
    public ResponseEntity<DriverMonthlyRapport> updateRapport(
            @PathVariable Long driverId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodTo) {

        try {
            DriverMonthlyRapport updatedRapport = service.updateRapport(driverId, periodFrom, periodTo);
            return new ResponseEntity<>(updatedRapport, HttpStatus.OK);
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
}
