package se.george.taxi.services;

import org.springframework.stereotype.Service;
import se.george.taxi.models.DriverDailyIncome;
import se.george.taxi.repositories.DriverDailyIncomeRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DriverDailyIncomeService {
    private final DriverDailyIncomeRepository dailyIncomeRepository;

    public DriverDailyIncomeService(DriverDailyIncomeRepository dailyIncomeRepository) {
        this.dailyIncomeRepository = dailyIncomeRepository;
    }

    public List<DriverDailyIncome> getAllDailyIncomes() {
        return dailyIncomeRepository.findAll();
    }

    public DriverDailyIncome saveDailyIncome(DriverDailyIncome dailyIncome) {
        dailyIncome.setDailyIncomeAfterVAT(
                dailyIncome.getDailyIncome().multiply( BigDecimal.valueOf(0.94))
        );
        dailyIncome.setDriverDailyProfit(
                dailyIncome.getDailyIncomeAfterVAT().multiply(BigDecimal.valueOf(0.45))
        );
        return dailyIncomeRepository.save(dailyIncome);
    }
}
