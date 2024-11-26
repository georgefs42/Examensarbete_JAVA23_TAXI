package se.george.taxi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "se.george.taxi.models")
public class TaxiApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaxiApplication.class, args);
    }
}