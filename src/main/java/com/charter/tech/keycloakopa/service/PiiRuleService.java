package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.entity.PiiRule;
import com.charter.tech.keycloakopa.repository.PiiRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PiiRuleService {
    private final PiiRuleRepository piiRuleRepository;

    @Transactional
    public Map<String, String> getAllPiiRule() {
        return piiRuleRepository.findAllByStatusIsTrue().collect(Collectors.toMap(PiiRule::getKey, PiiRule::getMasked));
    }

    public void createPiiRule(List<PiiRule> piiRule) {
        piiRuleRepository.saveAll(piiRule);
    }
}
