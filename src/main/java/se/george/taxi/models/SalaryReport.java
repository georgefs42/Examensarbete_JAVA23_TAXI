package se.george.taxi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class SalaryReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long driverId;
    private String name;
    private String personalNumber;

    private BigDecimal totalProfit;
    private BigDecimal tax;
    private BigDecimal salaryAfterTax;

    private LocalDate periodFrom;
    private LocalDate periodTo;
}
