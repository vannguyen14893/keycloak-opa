package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.annotaion.OPAAuthorize;
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

    @OPAAuthorize(resource = "pii-rule", action = "read")
    @GetMapping()
    public ResponseEntity<SuccessResultResponse<List<PiiRuleResponse>>> getListPiiRule() {
        return execute(piiRuleService.findAllPiiRule());
    }
    @OPAAuthorize(resource = "pii-rule", action = "read")
    @GetMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<PiiRuleResponse>> findPiiRuleById(@PathVariable Long id) {
        return execute(piiRuleService.findById(id));
    }
    @OPAAuthorize(resource = "pii-rule", action = "delete")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<Long>> delete(@PathVariable Long id) {
        return execute(piiRuleService.delete(id));
    }
    @OPAAuthorize(resource = "pii-rule", action = "create")
    @PostMapping()
    public ResponseEntity<SuccessResultResponse<PiiRuleResponse>> create(@RequestBody @Valid PiiRuleRequest piiRuleRequest) {
        return execute(piiRuleService.create(piiRuleRequest));
    }
    @OPAAuthorize(resource = "pii-rule", action = "update")
    @PutMapping(path = "/{id}")
    public ResponseEntity<SuccessResultResponse<PiiRuleResponse>> update(@PathVariable Long id, @RequestBody @Valid PiiRuleRequest piiRuleRequest) {
        return execute(piiRuleService.update(id, piiRuleRequest));
    }
}
