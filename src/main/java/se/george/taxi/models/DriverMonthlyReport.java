package se.george.taxi.models;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "driver_monthly_rapport")
public class DriverMonthlyReport {

    @Id
    @Column(name = "driver_id", nullable = false)
    private Long driverId; // Primary Key (also a Foreign Key)

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "personal_number", nullable = false)
    private String personalNumber;

    @Column(name = "total_profit", nullable = false)
    private BigDecimal totalProfit; // Sum of profits

    @Column(name = "period_from", nullable = false)
    private LocalDate periodFrom;

    @Column(name = "period_to", nullable = false)
    private LocalDate periodTo;

    // Constructors
    public DriverMonthlyReport () {
    }

    public DriverMonthlyReport (Long driverId, String name, String personalNumber, BigDecimal totalProfit, LocalDate periodFrom, LocalDate periodTo) {
        this.driverId = driverId;
        this.name = name;
        this.personalNumber = personalNumber;
        this.totalProfit = totalProfit;
        this.periodFrom = periodFrom;
        this.periodTo = periodTo;
    }

    // Getters and Setters
    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public LocalDate getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(LocalDate periodFrom) {
        this.periodFrom = periodFrom;
    }

    public LocalDate getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(LocalDate periodTo) {
        this.periodTo = periodTo;
    }

    // toString for debugging
    @Override
    public String toString() {
        return "DriverMonthlyRapport{" +
                "driverId=" + driverId +
                ", name='" + name + '\'' +
                ", personalNumber='" + personalNumber + '\'' +
                ", totalProfit=" + totalProfit +
                ", periodFrom=" + periodFrom +
                ", periodTo=" + periodTo +
                '}';
    }
}
