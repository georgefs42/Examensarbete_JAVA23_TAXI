package se.george.taxi.models;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "driver_daily_income")
@Data
public class DriverDailyIncome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private BigDecimal dailyIncome;

    @Column(nullable = false)
    private BigDecimal dailyIncomeAfterVAT;

    @Column(nullable = false)
    private BigDecimal driverDailyProfit;

    @Column(nullable = false)
    private BigDecimal monthlyDriverProfit;
}
