package com.charter.tech.keycloakopa.config;

import com.charter.tech.keycloakopa.client.OpaClient;
import com.charter.tech.keycloakopa.constans.LogAttributeConstants;
import com.charter.tech.keycloakopa.dto.HttpConfig;
import com.charter.tech.keycloakopa.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class HttpClientConfig {
    private final LoggingOutboundConfig loggingOutboundConfig;
    private final Utils utils;
    @Value("${app.opa.base-url}")
    private String opaUrl;

    @Bean
    public OpaClient opaClient() {
        HttpConfig httpConfig = new HttpConfig();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(httpConfig.getMaxTotalConnections());
        connectionManager.setDefaultMaxPerRoute(httpConfig.getDefaultMaxConnectionsPerRoute());
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(httpConfig.getConnectionTimeout(), TimeUnit.MILLISECONDS)
                .setResponseTimeout(httpConfig.getResponseTimeOut(), TimeUnit.MILLISECONDS)
                .build();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
        ClientHttpRequestFactory clientHttpRequestFactory = new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
        RestClient restClient = RestClient.builder().baseUrl(opaUrl)
                .requestFactory(clientHttpRequestFactory)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .requestInterceptor(loggingOutboundConfig)
                .requestInterceptor((request, body, execution) -> {
                    request.getHeaders().add(LogAttributeConstants.TRACE_ID, utils.getValueLog(LogAttributeConstants.TRACE_ID));
                    return execution.execute(request, body);
                }).build();
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();
        return httpServiceProxyFactory.createClient(OpaClient.class);
    }
}
