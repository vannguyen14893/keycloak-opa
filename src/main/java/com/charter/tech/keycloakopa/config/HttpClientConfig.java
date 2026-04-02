package com.charter.tech.keycloakopa.config;

import com.charter.tech.keycloakopa.client.OpaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@RequiredArgsConstructor
public class HttpClientConfig {
    private final LoggingInterceptor loggingInterceptor;

//    @Bean
//    public RestClient restClient() {
//        ClientHttpRequestFactory factory =
//                new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
//        return RestClient.builder()
//                .requestFactory(factory)
//                .requestInterceptor(new LoggingInterceptor())
//                .build();
//    }

    @Bean
    public OpaClient opaClient() {
        ClientHttpRequestFactory clientHttpRequestFactory =
                new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        RestClient restClient = RestClient.builder().baseUrl("http://localhost:8181")
                .requestFactory(clientHttpRequestFactory)
                .requestInterceptor(loggingInterceptor)
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();
        return factory.createClient(OpaClient.class);
    }
}
