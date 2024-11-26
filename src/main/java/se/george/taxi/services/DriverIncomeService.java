package se.george.taxi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.george.taxi.models.DriverIncome;
import se.george.taxi.repositories.DriverIncomeRepository;

import java.util.List;
@Service
public class DriverIncomeService {

    private final DriverIncomeRepository driverIncomeRepository;

    @Autowired
    public DriverIncomeService(DriverIncomeRepository driverIncomeRepository) {
        this.driverIncomeRepository = driverIncomeRepository;
    }

    public DriverIncome saveIncome(DriverIncome income) {
        income.setDailyIncomeAfterVAT(income.getDailyIncome() - (income.getDailyIncome() * 0.06));
        return driverIncomeRepository.save(income);
    }

    public List<DriverIncome> getMonthlyData(int year, int month) {
        return driverIncomeRepository.findAll(); // Adjust query as necessary
    }
}
