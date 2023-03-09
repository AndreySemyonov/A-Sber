package ru.astondevs.asber;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.astondevs.asber.filter.LoggingFilter;

@Configuration
@ConditionalOnProperty(value = "logging.filter.autoconfiguration", matchIfMissing = false)
public class LoggingFilterAutoConfiguration {

    @Bean
    public LoggingFilter makeLoggingFilter(){
        return new LoggingFilter();
    }
}
