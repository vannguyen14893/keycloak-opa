//package com.charter.tech.keycloakopa.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springdoc.core.customizers.OpenApiCustomizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.i18n.LocaleContextHolder;
//
//@Configuration
//@RequiredArgsConstructor
//public class SwaggerConfig {
//    private final DBMessageSourceConfig dbMessageSourceConfig;
//
//    //    @Bean
////    public OpenAPI openAPI() {
////        Locale locale = Locale.of("vn");
////        return new OpenAPI()
////                .info(new Info()
////                        .title(dbMessageSourceConfig.getMessages("api.title", null, locale))
////                        .description(dbMessageSourceConfig.getMessages("api.description", null, locale))
////                        .version(dbMessageSourceConfig.getMessages("api.version", null, locale))
////                        .contact(new Contact()
////                                .name(dbMessageSourceConfig.getMessages("api.contact.name", null, locale))
////                                .email(dbMessageSourceConfig.getMessages("api.contact.email", null, locale))));
////    }
//}
