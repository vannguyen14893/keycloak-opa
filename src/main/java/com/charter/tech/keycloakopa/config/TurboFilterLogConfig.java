package com.charter.tech.keycloakopa.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.turbo.TurboFilter;
import ch.qos.logback.core.spi.FilterReply;
import org.slf4j.MDC;
import org.slf4j.Marker;

import java.util.HashSet;
import java.util.Set;

public class TurboFilterLogConfig extends TurboFilter {
    public static final Set<String> HEALTH_URL = new HashSet<>(Set.of("/actuator/health"));

    @Override
    public FilterReply decide(
            Marker marker,
            Logger logger,
            Level level,
            String format,
            Object[] params,
            Throwable t) {
        String requestURI = MDC.get("requestUri");
        if (requestURI != null && HEALTH_URL.contains(requestURI)) {
            return FilterReply.DENY;
        }
        return FilterReply.NEUTRAL;
    }
}

