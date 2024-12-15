package se.george.taxi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.george.taxi.models.Driver;
import se.george.taxi.repositories.DriverRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    // Method to get all drivers
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    // Method to get a specific driver by ID
    public Driver getDriverById(Long driverId) {
        Optional<Driver> driver = driverRepository.findById(driverId);
        return driver.orElseThrow(() -> new RuntimeException("Driver not found with ID: " + driverId));
    }

    // Method to save a new driver
    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    // Method to update an existing driver
    public Driver updateDriver(Long driverId, Driver updatedDriver) {
        Optional<Driver> existingDriverOpt = driverRepository.findById(driverId);

        if (existingDriverOpt.isPresent()) {
            Driver existingDriver = existingDriverOpt.get();
            existingDriver.setName(updatedDriver.getName());
            existingDriver.setPersonalNumber(updatedDriver.getPersonalNumber());
            existingDriver.setAddress(updatedDriver.getAddress());
            existingDriver.setMobile(updatedDriver.getMobile());
            existingDriver.setEmail(updatedDriver.getEmail());
            existingDriver.setPhotoUrl(updatedDriver.getPhotoUrl());  // Update photoUrl

            return driverRepository.save(existingDriver);
        } else {
            throw new RuntimeException("Driver not found with ID: " + driverId);
        }
    }

    // Method to delete a driver
    public void deleteDriver(Long driverId) {
        if (driverRepository.existsById(driverId)) {
            driverRepository.deleteById(driverId);
        } else {
            throw new RuntimeException("Driver not found with ID: " + driverId);
        }
    }
}
