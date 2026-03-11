package com.charter.tech.keycloakopa;

import com.charter.tech.keycloakopa.entity.Languages;
import com.charter.tech.keycloakopa.entity.PiiRule;
import com.charter.tech.keycloakopa.service.LanguagesDetailService;
import com.charter.tech.keycloakopa.service.LanguagesService;
import com.charter.tech.keycloakopa.service.PiiRuleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.charter.tech.keycloakopa.config.DBMessageSourceConfig.LANGUAGES;
import static com.charter.tech.keycloakopa.service.MaskingConverter.PII_RULES;

@SpringBootApplication
@Slf4j
@EnableScheduling
@RequiredArgsConstructor
public class KeycloakOpaApplication {
    private final PiiRuleService piiRuleService;
    private final LanguagesService languagesService;
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
//        PiiRule piiRule1 = new PiiRule("cif", "******", true);
//        PiiRule piiRule2 = new PiiRule("email", "***@***.***", true);
//        PiiRule piiRule3 = new PiiRule("password", "******", true);
//        PiiRule piiRule4 = new PiiRule("cardNumber", "****-****-****-****", true);
//        PiiRule piiRule5 = new PiiRule("phone", "**** *** ***", true);
//        piiRuleService.createPiiRule(Arrays.asList(piiRule1, piiRule2, piiRule3, piiRule4, piiRule5));
        for (Languages languages : languagesService.findAll()) {
            String languagesCode = languages.getCode();
            Map<String, String> languagesDetails = languagesDetailService.findAllByLanguagesCode(languagesCode);
            LANGUAGES.put(languagesCode, languagesDetails);
        }

        PII_RULES.putAll(piiRuleService.getAllPiiRule());
        log.info("key {}", buildKey("amazon", UUID.randomUUID().toString()));
    }
}
