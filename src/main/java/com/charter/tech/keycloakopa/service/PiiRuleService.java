package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.dto.PiiRuleRequest;
import com.charter.tech.keycloakopa.dto.PiiRuleResponse;
import com.charter.tech.keycloakopa.entity.PiiRule;
import com.charter.tech.keycloakopa.mappper.PiiRuleMapper;
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
    private final PiiRuleMapper piiRuleMapper;

    @Transactional
    public Map<String, String> getAllPiiRule() {
        return piiRuleRepository.findAllByStatusIsTrue().collect(Collectors.toMap(PiiRule::getKey, PiiRule::getMasked));
    }

    public List<PiiRuleResponse> findAllPiiRule() {
        return piiRuleRepository.findAll().stream().map(this::convertToPiiRuleResponse).toList();
    }

    public PiiRuleResponse findById(Long id) {
        return convertToPiiRuleResponse(piiRuleRepository.findById(id).orElseThrow());
    }

    public Long delete(Long id) {
        PiiRule piiRule = piiRuleRepository.findById(id).orElseThrow();
        piiRule.setStatus(false);
        piiRuleRepository.save(piiRule);
        return id;
    }

    public PiiRuleResponse create(PiiRuleRequest piiRuleRequest) {
        PiiRule piiRule = piiRuleMapper.toEntityCreate(piiRuleRequest);
        return convertToPiiRuleResponse(piiRuleRepository.save(piiRule));
    }

    public PiiRuleResponse update(Long id, PiiRuleRequest piiRuleRequest) {
        PiiRule piiRule = piiRuleRepository.findById(id).orElseThrow();
        piiRuleMapper.toEntityUpdate(piiRuleRequest, piiRule);
        return convertToPiiRuleResponse(piiRuleRepository.save(piiRule));
    }

    public PiiRuleResponse convertToPiiRuleResponse(PiiRule piiRule) {
        return piiRuleMapper.toResponse(piiRule);
    }
}
