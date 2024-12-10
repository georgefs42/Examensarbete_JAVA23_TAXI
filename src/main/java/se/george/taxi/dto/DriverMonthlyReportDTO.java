package se.george.taxi.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO representing a driver's monthly rapport details.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverMonthlyReportDTO {

    @NotNull(message = "Driver ID cannot be null")
    private Long driverId;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Personal number cannot be blank")
    @Pattern(regexp = "\\d{10}", message = "Personal number must be a 10-digit number")
    private String personalNumber;

    @NotNull(message = "Total profit cannot be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Total profit must be a non-negative value")
    private BigDecimal totalProfit;

    @NotNull(message = "Period start date cannot be null")
    private LocalDate periodFrom;

    @NotNull(message = "Period end date cannot be null")
    private LocalDate periodTo;
}
