package com.charter.tech.keycloakopa.config;

import com.charter.tech.keycloakopa.client.OpaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpClientConfig {
    @Bean
    public OpaClient opaClient() {
        RestClient restClient = RestClient.builder().baseUrl("http://localhost:8181").build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();
        return factory.createClient(OpaClient.class);
    }
}
