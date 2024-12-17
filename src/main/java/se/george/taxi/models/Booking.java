package se.george.taxi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String mobile;
    private String email;
    private String pickupAddress;
    private String extraAddress;
    private String dropOffAddress;
    private double distance;
    private double duration;
    private double price;

    private LocalDate date; // New field for date
    private LocalTime time; // New field for time

    // Default constructor
    public Booking() {}

    // Constructor with fields
    public Booking(String name, String mobile, String email, String pickupAddress, String extraAddress,
                   String dropOffAddress, double distance, double duration, double price,
                   LocalDate date, LocalTime time) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.pickupAddress = pickupAddress;
        this.extraAddress = extraAddress;
        this.dropOffAddress = dropOffAddress;
        this.distance = distance;
        this.duration = duration;
        this.price = price;
        this.date = date;
        this.time = time;
    }
}
