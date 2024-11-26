package se.george.taxi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driverId;

    private String name;
    private String personalNumber;
    private String address;
    private String mobile;
    private String email;

    @Lob
    private byte[] photo;

    // Getters and Setters

    public Long getDriverId () {
        return driverId;
    }

    public void setDriverId (Long driverId) {
        this.driverId = driverId;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getPersonalNumber () {
        return personalNumber;
    }

    public void setPersonalNumber (String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getAddress () {
        return address;
    }

    public void setAddress (String address) {
        this.address = address;
    }

    public String getMobile () {
        return mobile;
    }

    public void setMobile (String mobile) {
        this.mobile = mobile;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public byte[] getPhoto () {
        return photo;
    }

    public void setPhoto (byte[] photo) {
        this.photo = photo;
    }
}
