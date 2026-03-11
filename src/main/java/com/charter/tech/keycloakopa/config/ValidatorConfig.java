package com.charter.tech.keycloakopa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Configuration class for setting up validation beans.
 * Provides custom validator configuration with database message source.
 */
@Configuration
public class ValidatorConfig {
    /**
     * Creates and configures a LocalValidatorFactoryBean with custom message source.
     *
     * @return configured Validator instance for bean validation
     */
    @Bean
    public Validator getValidator() {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.setValidationMessageSource(new DBMessageSourceConfig());
        return factory;
    }
}
