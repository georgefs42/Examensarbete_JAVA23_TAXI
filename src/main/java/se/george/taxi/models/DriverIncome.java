package se.george.taxi.models;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
public class DriverIncome {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private double dailyIncome;

    @Column(name = "daily_income_after_vat", nullable = false)
    private double dailyIncomeAfterVAT;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(name = "driver_daily_profit")
    private double driverDailyProfit; // New field for daily profit

    // Constructors
    public DriverIncome() {}

    public DriverIncome(LocalDate date, double dailyIncome, Driver driver) {
        this.date = date;
        this.dailyIncome = dailyIncome;
        this.driver = driver;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDailyIncome(double dailyIncome) {
        this.dailyIncome = dailyIncome;
    }

    public void setDailyIncomeAfterVAT(double dailyIncomeAfterVAT) {
        this.dailyIncomeAfterVAT = dailyIncomeAfterVAT;
        // Calculate the driver's daily profit
        calculateDriverDailyProfit();
    }

    public void setDriverDailyProfit(double driverDailyProfit) {
        this.driverDailyProfit = driverDailyProfit;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    // Method to calculate the driver's daily profit (45% of dailyIncomeAfterVAT)
    private void calculateDriverDailyProfit() {
        this.driverDailyProfit = this.dailyIncomeAfterVAT * 0.45; // 45% of dailyIncomeAfterVAT
    }

    @PrePersist
    @PreUpdate
    public void calculateDailyIncomeAfterVATAndProfit() {
        // Calculate VAT (6%) deduction from dailyIncome
        this.dailyIncomeAfterVAT = this.dailyIncome - (this.dailyIncome * 0.06);

        // Calculate the driver's daily profit
        calculateDriverDailyProfit();
    }
}
