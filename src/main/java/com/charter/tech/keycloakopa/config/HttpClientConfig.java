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

    @Bean
    public OpaClient opaClient() {
        ClientHttpRequestFactory clientHttpRequestFactory =
                new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        RestClient restClient = RestClient.builder().baseUrl("http://localhost:8082")
                .requestFactory(clientHttpRequestFactory)
                .requestInterceptor(loggingInterceptor)
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();
        return factory.createClient(OpaClient.class);
    }
}
