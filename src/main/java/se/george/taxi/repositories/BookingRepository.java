package se.george.taxi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.george.taxi.models.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    // You can add custom queries here if needed, for example:
    // List<Booking> findByName(String name);
}
