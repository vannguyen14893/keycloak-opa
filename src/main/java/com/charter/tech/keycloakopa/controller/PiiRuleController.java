package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.dto.PiiRuleRequest;
import com.charter.tech.keycloakopa.dto.PiiRuleResponse;
import com.charter.tech.keycloakopa.dto.SuccessResultResponse;
import com.charter.tech.keycloakopa.service.PiiRuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/pii-rule")
public class PiiRuleController extends BaseController {
    private final PiiRuleService piiRuleService;

    @GetMapping()
    public ResponseEntity<SuccessResultResponse<List<PiiRuleResponse>>> getListPiiRule() {
        return execute(piiRuleService.findAllPiiRule());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<PiiRuleResponse>> findPiiRuleById(@PathVariable Long id) {
        return execute(piiRuleService.findById(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<Long>> delete(@PathVariable Long id) {
        return execute(piiRuleService.delete(id));
    }

    @PostMapping()
    public ResponseEntity<SuccessResultResponse<PiiRuleResponse>> create(@RequestBody @Valid PiiRuleRequest piiRuleRequest) {
        return execute(piiRuleService.create(piiRuleRequest));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<PiiRuleResponse>> update(@PathVariable Long id, @RequestBody @Valid PiiRuleRequest piiRuleRequest) {
        return execute(piiRuleService.update(id, piiRuleRequest));
    }
}
