package com.charter.tech.keycloakopa.client;

import com.charter.tech.keycloakopa.dto.OpaResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;

@HttpExchange()
public interface OpaClient {
    @PostExchange(value = "/v1/data/authz/allow", accept = "application/json")
    OpaResponse allow(@RequestBody Map<String, Object> input);
}

