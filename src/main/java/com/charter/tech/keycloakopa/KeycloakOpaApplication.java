package com.charter.tech.keycloakopa;
import com.charter.tech.keycloakopa.entity.LanguagesDetail;
import com.charter.tech.keycloakopa.service.LanguagesDetailService;
import com.charter.tech.keycloakopa.service.PiiRuleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.resilience.annotation.EnableResilientMethods;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.*;
import java.util.stream.Collectors;

import static com.charter.tech.keycloakopa.config.DBMessageSourceConfig.LANGUAGES;
import static com.charter.tech.keycloakopa.config.TurboFilterLogConfig.HEALTH_URL;
import static com.charter.tech.keycloakopa.config.MaskingConverterLogConfig.PII_RULES;

@SpringBootApplication
@Slf4j
@EnableScheduling
@RequiredArgsConstructor
@EnableResilientMethods
public class KeycloakOpaApplication {

    private final PiiRuleService piiRuleService;

    private final LanguagesDetailService languagesDetailService;

    Set<String> hotKeys = Set.of("amazon");
    int splitFactor = 4;

    public static void main(String[] args) {

        SpringApplication.run(KeycloakOpaApplication.class, args);
    }



    public String buildKey(String merchantId, String orderId) {

        if (hotKeys.contains(merchantId)) {
            int bucket = Math.abs(orderId.hashCode()) % splitFactor;
            return merchantId + "#" + bucket;
        }

        return merchantId;
    }

    @PostConstruct
    public void initDataPiiRule() {
        Map<String, List<LanguagesDetail>> languagesDetailsMap = languagesDetailService.findAllByService();
        for (String languagesCode : languagesDetailsMap.keySet()) {
            Map<String, String> languagesDetails = languagesDetailsMap.get(languagesCode).stream().collect(Collectors.toMap(LanguagesDetail::getKey, LanguagesDetail::getValue));
            LANGUAGES.put(languagesCode, languagesDetails);
        }

        PII_RULES.putAll(piiRuleService.getAllPiiRule());
        HEALTH_URL.addAll(Set.of("/settings", "/orders", "/categories"));

        log.info("key {}", buildKey("amazon", UUID.randomUUID().toString()));
    }
}
