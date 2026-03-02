package com.charter.tech.keycloakopa.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class PiiRuleService {
    public static final Map<String, String> PII_RULES = new HashMap<>();

    public Set<String> getPiiKeysDefault() {
        return getPiiRuleDefault().keySet();
    }

    public Map<String, String> getPiiRuleDefault() {
        PII_RULES.put("cif", "******");
        return PII_RULES;
    }
}
