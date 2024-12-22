package se.george.taxi.controllers;

import se.george.taxi.models.Booking;
import se.george.taxi.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "https://taxi.georgedev.se")
//@CrossOrigin(origins = "http://localhost:5173")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    // Create a new booking
    @PostMapping
    public ResponseEntity<Map<String, Object>> createBooking(@RequestBody Booking booking) {
        try {
            // Save the booking in the database
            Booking savedBooking = bookingRepository.save(booking);

            // Create a response map to return the complete booking details, including date and time
            Map<String, Object> response = new HashMap<>();
            response.put("id", savedBooking.getId());
            response.put("name", savedBooking.getName());
            response.put("mobile", savedBooking.getMobile());
            response.put("email", savedBooking.getEmail());
            response.put("pickupAddress", savedBooking.getPickupAddress());
            response.put("extraAddress", savedBooking.getExtraAddress());
            response.put("dropOffAddress", savedBooking.getDropOffAddress());
            response.put("distance", savedBooking.getDistance());
            response.put("duration", savedBooking.getDuration());
            response.put("price", savedBooking.getPrice());
            response.put("date", savedBooking.getDate()); // Include date in the response
            response.put("time", savedBooking.getTime()); // Include time in the response
            response.put("message", "Booking received successfully!");

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error creating booking: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    // Retrieve all bookings
    @GetMapping
    public ResponseEntity<?> getAllBookings() {
        try {
            List<Booking> bookings = bookingRepository.findAll();
            if (bookings.isEmpty()) {
                return new ResponseEntity<>("No bookings found", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error fetching bookings: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Retrieve a single booking by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Long id) {
        try {
            Booking booking = bookingRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Booking not found"));

            return new ResponseEntity<>(booking, HttpStatus.OK);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Booking not found: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    // Update an existing booking
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        if (!bookingRepository.existsById(id)) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Booking not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        booking.setId(id); // Make sure the ID is set before saving
        Booking updatedBooking = bookingRepository.save(booking);

        Map<String, Object> response = new HashMap<>();
        response.put("id", updatedBooking.getId());
        response.put("message", "Booking updated successfully!");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete a booking
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteBooking(@PathVariable Long id) {
        if (!bookingRepository.existsById(id)) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Booking not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        bookingRepository.deleteById(id); // Delete the booking by ID

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Booking deleted successfully! ID: " + id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
