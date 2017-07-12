package de.tu_berlin.ise.open_data.config;

import de.tu_berlin.ise.open_data.service.ApplicationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {
    @Bean
    public ApplicationServiceImpl service() {
        return new ApplicationServiceImpl();
    }
}
