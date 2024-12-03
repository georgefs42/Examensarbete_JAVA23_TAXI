package se.george.taxi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.george.taxi.models.MonthlyRapport;
import se.george.taxi.repositories.MonthlyRapportRepository;

import java.util.List;

@Service
public class MonthlyRapportService {

    private final MonthlyRapportRepository monthlyRapportRepository;

    @Autowired
    public MonthlyRapportService(MonthlyRapportRepository monthlyRapportRepository) {
        this.monthlyRapportRepository = monthlyRapportRepository;
    }

    public MonthlyRapport saveMonthlyRapport(MonthlyRapport rapport) {
        return monthlyRapportRepository.save(rapport);
    }

    public List<MonthlyRapport> getAllMonthlyRapports() {
        return monthlyRapportRepository.findAll();
    }

    public List<MonthlyRapport> getMonthlyRapportByDriver(Long driverId, int year, int month) {
        // Custom query can be added here to filter by driver and date range
        return monthlyRapportRepository.findAll();
    }
}
