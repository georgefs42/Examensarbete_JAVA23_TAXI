package se.george.taxi.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class SalaryReportDTO {

    @NotNull(message = "Driver ID cannot be null")
    private Long driverId;

    @NotNull(message = "Month cannot be null")
    @Min(1)
    @Max(12)
    private int month;

    @NotNull(message = "Year cannot be null")
    private int year;

    @NotNull(message = "Tax cannot be null")
    private BigDecimal tax;
}
