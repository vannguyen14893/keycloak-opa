package com.charter.tech.keycloakopa.dto;

import lombok.Data;

@Data
public class HttpConfig {
    private int maxTotalConnections = 10;
    private int defaultMaxConnectionsPerRoute = 10;
    private int connectionTimeout = 5000; // milliseconds
    private int responseTimeOut = 6000; // milliseconds
}
