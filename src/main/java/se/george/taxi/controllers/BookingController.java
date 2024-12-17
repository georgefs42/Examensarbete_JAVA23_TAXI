package se.george.taxi.controllers;

import se.george.taxi.models.Booking;
import se.george.taxi.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:5173")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;  // Inject the BookingRepository

    // Create a new booking
    @PostMapping
    public ResponseEntity<Map<String, Object>> createBooking(@RequestBody Booking booking) {
        try {
            // Save the booking in the database
            Booking savedBooking = bookingRepository.save(booking);

            // Create a response map to return the complete booking details
            Map<String, Object> response = new HashMap<>();
            response.put("name", savedBooking.getName());
            response.put("mobile", savedBooking.getMobile());
            response.put("email", savedBooking.getEmail());
            response.put("pickupAddress", savedBooking.getPickupAddress());
            response.put("extraAddress", savedBooking.getExtraAddress());
            response.put("dropOffAddress", savedBooking.getDropOffAddress());
            response.put("distance", savedBooking.getDistance());
            response.put("duration", savedBooking.getDuration());
            response.put("price", savedBooking.getPrice());
            response.put("message", "Booking received successfully!");

            // Return the response with status 201 (Created)
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            // In case of an error, return a bad request response
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error creating booking: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    // Retrieve all bookings
    @GetMapping
    public ResponseEntity<?> getAllBookings() {
        try {
            List<Booking> bookings = bookingRepository.findAll();  // Fetch all bookings from the database
            if (bookings.isEmpty()) {
                // If no bookings are found, return a 204 No Content response
                return new ResponseEntity<>("No bookings found", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bookings, HttpStatus.OK);  // Return 200 OK with the list of bookings
        } catch (Exception e) {
            // Return a generic error response with status 500
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
                    .orElseThrow(() -> new RuntimeException("Booking not found")); // Fetch booking by ID

            return new ResponseEntity<>(booking, HttpStatus.OK);  // Return the booking with status 200
        } catch (RuntimeException e) {
            // Return an error message if the booking is not found
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

        booking.setId(id);  // Make sure the ID is set before saving
        Booking updatedBooking = bookingRepository.save(booking); // Save the updated booking

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

        bookingRepository.deleteById(id);  // Delete the booking by ID

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Booking deleted successfully! ID: " + id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
