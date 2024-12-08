package se.george.taxi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.george.taxi.models.DriverIncome;
import java.time.LocalDate;

@Repository
public interface DriverIncomeRepository extends JpaRepository<DriverIncome, Long> {

    // Custom query to calculate total profit in a given period
    @Query("SELECT SUM(d.driverDailyProfit) FROM DriverIncome d WHERE d.driver.id = :driverId AND d.date BETWEEN :periodFrom AND :periodTo")
    Double calculateTotalProfit(@Param("driverId") Long driverId,
                                @Param("periodFrom") LocalDate periodFrom,
                                @Param("periodTo") LocalDate periodTo);
}
