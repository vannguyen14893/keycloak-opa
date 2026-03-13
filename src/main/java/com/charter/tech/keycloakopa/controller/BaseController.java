package com.charter.tech.keycloakopa.controller;

import com.charter.tech.keycloakopa.config.DBMessageSourceConfig;
import com.charter.tech.keycloakopa.constans.MessageConstants;
import com.charter.tech.keycloakopa.constans.ResponseCodeConstants;
import com.charter.tech.keycloakopa.dto.SuccessResultResponse;
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
    DBMessageSourceConfig dbMessageSourceConfig;
    @Autowired
    HttpServletRequest httpServletRequest;

    /**
     * Creates a ResponseEntity with a ResponseSuccess wrapper for single response objects.
     *
     * @param <T>      the type of response data
     * @param response the response data to be wrapped
     * @return ResponseEntity containing the wrapped response
     */
    public <T> ResponseEntity<SuccessResultResponse<T>> execute(T response) {
        var locale = httpServletRequest.getLocale();
        String messages = dbMessageSourceConfig.getMessages(MessageConstants.MESSAGE_SUCCESS, null, locale);
        return new ResponseEntity<>(new SuccessResultResponse<>(MDC.get("trace_id"), ResponseCodeConstants.CODE_SUCCESS, messages, response), HttpStatusCode.valueOf(200));
    }
}
