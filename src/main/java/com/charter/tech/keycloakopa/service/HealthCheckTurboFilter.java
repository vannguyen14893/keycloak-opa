package com.charter.tech.keycloakopa.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.MDC;
import org.slf4j.Marker;

public class HealthCheckTurboFilter extends TurboFilter {
    private static final String HEALTH_URL = "/actuator/health";
    private String mdcKey = "requestUri";

    @Override
    public FilterReply decide(
            Marker marker,
            Logger logger,
            Level level,
            String format,
            Object[] params,
            Throwable t) {
        String requestURI = MDC.get(mdcKey);
        if (requestURI != null && requestURI.contains(HEALTH_URL)) {
            return FilterReply.DENY;
        }
        return FilterReply.NEUTRAL;
    }
}

