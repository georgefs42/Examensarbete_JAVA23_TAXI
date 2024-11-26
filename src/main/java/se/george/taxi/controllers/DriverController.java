package se.george.taxi.controllers;

import org.springframework.web.bind.annotation.*;
import se.george.taxi.models.Driver;
import se.george.taxi.services.DriverService;

import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    // GET: Retrieve all drivers
    @GetMapping
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    // GET: Retrieve a specific driver by ID
    @GetMapping("/{driverId}")
    public Driver getDriverById(@PathVariable Long driverId) {
        return driverService.getDriverById(driverId);
    }

    // POST: Create a new driver
    @PostMapping
    public Driver createDriver(@RequestBody Driver driver) {
        return driverService.saveDriver(driver);
    }

    // PUT: Update an existing driver
    @PutMapping("/{driverId}")
    public Driver updateDriver(@PathVariable Long driverId, @RequestBody Driver updatedDriver) {
        return driverService.updateDriver(driverId, updatedDriver);
    }

    // DELETE: Delete a driver by ID
    @DeleteMapping("/{driverId}")
    public void deleteDriver(@PathVariable Long driverId) {
        driverService.deleteDriver(driverId);
    }
}
