package se.george.taxi.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class DriverMonthlyRapportDTO {
    private Long driverId;
    private String name;
    private String personalNumber;
    private BigDecimal totalProfit;
    private LocalDate periodFrom;
    private LocalDate periodTo;
}
