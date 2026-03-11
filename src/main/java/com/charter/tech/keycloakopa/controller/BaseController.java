package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.config.DBMessageSourceConfig;
import com.charter.tech.keycloakopa.dto.ResultResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

/**
 * Base controller class providing common response handling functionality for REST endpoints.
 */

public class BaseController {
    @Autowired
    private DBMessageSourceConfig dbMessageSourceConfig;
    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * Creates a ResponseEntity with a ResponseSuccess wrapper for single response objects.
     *
     * @param <T>      the type of response data
     * @param response the response data to be wrapped
     * @param status   the HTTP status code as a string
     * @return ResponseEntity containing the wrapped response
     */
    public <T> ResponseEntity<ResultResponse<T>> execute(T response, String key, String status) {
        var locale = httpServletRequest.getLocale();
        String messages = dbMessageSourceConfig.getMessages(key, null, locale);
        return new ResponseEntity<>(new ResultResponse<>(MDC.get("trace_id"), status, messages, response), HttpStatusCode.valueOf(Integer.parseInt(status)));
    }
}
