package com.charter.tech.keycloakopa.config;

import com.charter.tech.keycloakopa.service.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import java.io.IOException;
@Component
@Slf4j
public class LoggingInterceptor implements ClientHttpRequestInterceptor {
    @Autowired
    private LogService logService;
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        if (logService.shouldSkip(request.getURI().getPath())) {
            return execution.execute(request, body);
        }
        long startTime = System.currentTimeMillis();
        ClientHttpResponse response = execution.execute(request, body);
        ServletRequestAttributes attrs =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(attrs.getRequest(),0);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(attrs.getResponse());
        long duration = System.currentTimeMillis() - startTime;
        logService.logCombined(wrappedRequest, wrappedResponse, duration);
        //MDC.clear();
        return response;
    }
}
