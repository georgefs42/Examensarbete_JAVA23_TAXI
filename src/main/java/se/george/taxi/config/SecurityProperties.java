package se.george.taxi.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class SecurityProperties {

    // Admin user credentials
    @Value("${security.admin.username}")
    private String adminUsername;

    @Value("${security.admin.password}")
    private String adminPassword;

    // Taxi Central user credentials
    @Value("${security.taxiCentral.username}")
    private String taxiCentralUsername;

    @Value("${security.taxiCentral.password}")
    private String taxiCentralPassword;

    // Driver credentials
    @Value("${security.driver1.username}")
    private String driver1Username;

    @Value("${security.driver1.password}")
    private String driver1Password;

    @Value("${security.driver2.username}")
    private String driver2Username;

    @Value("${security.driver2.password}")
    private String driver2Password;

    @Value("${security.driver3.username}")
    private String driver3Username;

    @Value("${security.driver3.password}")
    private String driver3Password;

    // Getters for all properties

}
