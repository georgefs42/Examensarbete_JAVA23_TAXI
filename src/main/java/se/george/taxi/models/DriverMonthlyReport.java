package se.george.taxi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "driver_monthly_rapport")
public class DriverMonthlyReport {

    // Getters and Setters
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
