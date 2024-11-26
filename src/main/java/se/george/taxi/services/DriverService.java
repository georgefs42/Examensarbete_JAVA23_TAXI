package se.george.taxi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.george.taxi.models.Driver;
import se.george.taxi.repositories.DriverRepository;

import java.util.List;

@Service
public class DriverService {
    private final DriverRepository driverRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    // Save a new driver
    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    // Retrieve all drivers
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    // Retrieve a driver by ID
    public Driver getDriverById(Long driverId) {
        return driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found with ID: " + driverId));
    }

    // Update an existing driver
    public Driver updateDriver(Long driverId, Driver updatedDriver) {
        Driver existingDriver = getDriverById(driverId);

        existingDriver.setName(updatedDriver.getName());
        existingDriver.setPersonalNumber(updatedDriver.getPersonalNumber());
        existingDriver.setAddress(updatedDriver.getAddress());
        existingDriver.setMobile(updatedDriver.getMobile());
        existingDriver.setEmail(updatedDriver.getEmail());
        existingDriver.setPhoto(updatedDriver.getPhoto());

        return driverRepository.save(existingDriver);
    }

    // Delete a driver by ID
    public void deleteDriver(Long driverId) {
        if (!driverRepository.existsById(driverId)) {
            throw new RuntimeException("Driver not found with ID: " + driverId);
        }
        driverRepository.deleteById(driverId);
    }
}
