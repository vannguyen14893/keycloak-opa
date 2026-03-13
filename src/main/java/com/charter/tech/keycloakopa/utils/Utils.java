package com.charter.tech.keycloakopa.utils;

import com.charter.tech.keycloakopa.annotaion.ApiModule;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Utils {
    public String getModuleName(Object target) {
        ApiModule moduleAnnotation = target.getClass().getAnnotation(ApiModule.class);
        String module = moduleAnnotation != null ? moduleAnnotation.value() : "COMMON";
        String errorCode = "VAL_" + module + "_001";
        log.info("errorCode: {}", errorCode);
        return errorCode;
    }

    public String getValueLog(String attributeLog) {
        return MDC.get(attributeLog);
    }
}
