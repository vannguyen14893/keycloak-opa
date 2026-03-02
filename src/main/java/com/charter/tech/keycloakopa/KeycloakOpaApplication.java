package com.charter.tech.keycloakopa;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import static com.charter.tech.keycloakopa.service.PiiRuleService.PII_RULES;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class KeycloakOpaApplication {

    public static void main(String[] args) {

        SpringApplication.run(KeycloakOpaApplication.class, args);
    }

    @PostConstruct
    public void initDataPiiRule() {
        PII_RULES.put("email", "***@***.***");
        PII_RULES.put("password", "******");
        PII_RULES.put("cardNumber", "****-****-****-****");
        PII_RULES.put("phone", "**** *** ***");
    }
}
