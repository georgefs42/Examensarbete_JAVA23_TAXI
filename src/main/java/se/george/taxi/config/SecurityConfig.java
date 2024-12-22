package se.george.taxi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/taxiCentral/**").hasRole("TAXI_CENTRAL")
                .requestMatchers("/driver/**").hasAnyRole("DRIVER1", "DRIVER2", "DRIVER3")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")  // POST login request URL
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .cors()  // Enable CORS globally
                .and()
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);  // Add custom filter for handling login requests

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService())  // Inject user details service
                .passwordEncoder(passwordEncoder());  // Inject password encoder
        return authenticationManagerBuilder.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                createUser(securityProperties.getAdminUsername(), securityProperties.getAdminPassword(), "ADMIN"),
                createUser(securityProperties.getTaxiCentralUsername(), securityProperties.getTaxiCentralPassword(), "TAXI_CENTRAL"),
                createUser(securityProperties.getDriver1Username(), securityProperties.getDriver1Password(), "DRIVER1"),
                createUser(securityProperties.getDriver2Username(), securityProperties.getDriver2Password(), "DRIVER2"),
                createUser(securityProperties.getDriver3Username(), securityProperties.getDriver3Password(), "DRIVER3")
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // BCrypt encoder for password hashing
    }

    private User createUser(String username, String password, String role) {
        return (User) User.withUsername(username)
                .password(passwordEncoder().encode(password))  // Ensure password is encoded
                .roles(role)
                .build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:5173");  // Allow frontend URL
            }
        };
    }

    // Custom authentication filter to handle login responses (success/failure)
    public UsernamePasswordAuthenticationFilter customAuthenticationFilter() throws Exception {
        UsernamePasswordAuthenticationFilter authenticationFilter = new UsernamePasswordAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManager(null));
        authenticationFilter.setFilterProcessesUrl("/login");  // Custom login URL
        authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        authenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        return authenticationFilter;
    }

    // Success handler for login
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            response.setStatus(HttpStatus.OK.value());  // OK status for successful login
            response.getWriter().write("Login successful");
        };
    }

    // Failure handler for login
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());  // Unauthorized status for failed login
            response.getWriter().write("Invalid username or password");
        };
    }
}
