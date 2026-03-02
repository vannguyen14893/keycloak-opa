package com.charter.tech.keycloakopa.client;

import com.charter.tech.keycloakopa.dto.OpaResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Component
public class OpaClient {

    public boolean allow(Map<String, Object> input) {
        RestTemplate restTemplate =new RestTemplate();
        return Objects.requireNonNull(restTemplate.exchange("http://localhost:8181/v1/data/authz/allow", HttpMethod.POST, new HttpEntity<>(input, new HttpHeaders()), OpaResponse.class).getBody()).isResult();
    }
}

