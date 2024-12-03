package se.george.taxi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "monthly_rapport")
public class MonthlyRapport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int year;
    private int month;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    private double totalMonthlyIncome;
    private double totalDriverProfit;

    // Constructors
    public MonthlyRapport() {}

    public MonthlyRapport(int year, int month, Driver driver) {
        this.year = year;
        this.month = month;
        this.driver = driver;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public double getTotalMonthlyIncome() {
        return totalMonthlyIncome;
    }

    public void setTotalMonthlyIncome(double totalMonthlyIncome) {
        this.totalMonthlyIncome = totalMonthlyIncome;
    }

    public double getTotalDriverProfit() {
        return totalDriverProfit;
    }

    public void setTotalDriverProfit(double totalDriverProfit) {
        this.totalDriverProfit = totalDriverProfit;
    }
}
