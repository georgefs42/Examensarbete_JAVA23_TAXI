package se.george.taxi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers("/income.html").authenticated()  // Protect the income page
                .anyRequest().permitAll()  // Allow other pages (like login.html) to be accessed without authentication
                .and()
                .formLogin()
                .loginPage("/login.html")  // Custom login page
                .loginProcessingUrl("/login")  // URL to process the form submission
                .defaultSuccessUrl("/income.html", true)  // Redirect to income.html after successful login
                .permitAll()  // Allow anyone to access the login page
                .and()
                .httpBasic();  // You can still allow basic authentication for APIs
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // Define an in-memory user with username "user" and password "123456"
        var user = User.builder()
                .username("user")  // Username is now "user"
                .password(passwordEncoder.encode("123456"))  // Password is encoded
                .roles("USER")  // Optional: You can define roles if needed
                .build();
        return new InMemoryUserDetailsManager(user);  // Return user details manager with this user
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Use BCrypt for password encoding
    }
}
