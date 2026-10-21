package org.example.ConstructorInjection;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Address address() {

        return new Address("Lucknow", "Uttar Pradesh");
    }

    @Bean
    public Employee employee() {

        return new Employee(101, "Satyam", address());
    }
}