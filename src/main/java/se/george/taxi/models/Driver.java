package se.george.taxi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Driver {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driverId;  // Primary key

    private String name;
    private String personalNumber;
    private String address;
    private String mobile;
    private String email;

    // Store the photo URL instead of the photo itself
    private String photoUrl;  // Photo URL

}
