package com.charter.tech.keycloakopa.config;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class OpenTelemetryInstaller implements InitializingBean {

    private final OpenTelemetry openTelemetry;

    public OpenTelemetryInstaller(OpenTelemetry openTelemetry) {
        this.openTelemetry = openTelemetry;
    }

    @Override
    public void afterPropertiesSet() {
        GlobalOpenTelemetry.set(openTelemetry);
    }
}