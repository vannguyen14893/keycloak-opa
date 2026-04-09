package com.charter.tech.keycloakopa.client;

import com.charter.tech.keycloakopa.dto.ErrorCatalogResponse;
import com.charter.tech.keycloakopa.dto.OpaResponse;
import com.charter.tech.keycloakopa.dto.SuccessResultResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;

@HttpExchange()
public interface OpaClient {
    @PostExchange(value = "/v1/data/authz/allow")
    OpaResponse allow(@RequestBody Map<String, Object> input);

    @GetExchange(value = "/v1/error-catalog/{id}")
    SuccessResultResponse<ErrorCatalogResponse> findErrorCatalogById(@PathVariable Long id);
}

