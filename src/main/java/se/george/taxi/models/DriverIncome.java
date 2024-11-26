package se.george.taxi.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class DriverIncome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String name;
    private double dailyIncome;

    @Column(name = "daily_income_aftervat", nullable = false)
    private double dailyIncomeAfterVAT;

    // Constructors
    public DriverIncome() {}

    public DriverIncome(LocalDate date, String name, double dailyIncome) {
        this.date = date;
        this.name = name;
        this.dailyIncome = dailyIncome;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDailyIncome() {
        return dailyIncome;
    }

    public void setDailyIncome(double dailyIncome) {
        this.dailyIncome = dailyIncome;
    }

    public double getDailyIncomeAfterVAT() {
        return dailyIncomeAfterVAT;
    }

    public void setDailyIncomeAfterVAT(double dailyIncomeAfterVAT) {
        this.dailyIncomeAfterVAT = dailyIncomeAfterVAT;
    }

    // Calculate the value of dailyIncomeAfterVAT (e.g., 6% VAT deduction)
    @PrePersist
    public void calculateDailyIncomeAfterVAT() {
        this.dailyIncomeAfterVAT = this.dailyIncome - (this.dailyIncome * 0.06);
    }

    public double calculateDailyDriverProfit() {
        return dailyIncomeAfterVAT * 0.45;
    }
}
