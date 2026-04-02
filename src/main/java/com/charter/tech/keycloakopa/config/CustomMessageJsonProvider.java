package com.charter.tech.keycloakopa.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.core.JsonGenerator;
import net.logstash.logback.composite.loggingevent.MessageJsonProvider;

import java.io.IOException;

public class CustomMessageJsonProvider extends MessageJsonProvider {
    @Override
    public void writeTo(JsonGenerator generator, ILoggingEvent event) throws IOException {
        String message = event.getFormattedMessage();
        message = new MaskingConverterLogConfig().maskSensitiveData(message);
        generator.writeStringField("message", message);
    }
}
