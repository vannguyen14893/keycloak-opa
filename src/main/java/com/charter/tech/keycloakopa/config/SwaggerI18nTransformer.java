package com.charter.tech.keycloakopa.config;

import com.charter.tech.keycloakopa.service.SwaggerTranslationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiOAuthProperties;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springdoc.webmvc.ui.SwaggerIndexPageTransformer;
import org.springdoc.webmvc.ui.SwaggerWelcomeCommon;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.resource.ResourceTransformerChain;
import org.springframework.web.servlet.resource.TransformedResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class SwaggerI18nTransformer extends SwaggerIndexPageTransformer {

    public SwaggerI18nTransformer(SwaggerUiConfigProperties swaggerUiConfig, SwaggerUiOAuthProperties swaggerUiOAuthProperties,
                                  SwaggerWelcomeCommon swaggerWelcomeCommon, ObjectMapperProvider objectMapperProvider) {
        super(swaggerUiConfig, swaggerUiOAuthProperties, swaggerWelcomeCommon, objectMapperProvider);
    }


    @Override
    public Resource transform(
            HttpServletRequest request,
            Resource resource,
            ResourceTransformerChain chain)
            throws IOException {
        Resource transformed = super.transform(request, resource, chain);
        if (!resource.getFilename().contains("index"))
            return transformed;
        String html = StreamUtils.copyToString(
                transformed.getInputStream(), StandardCharsets.UTF_8);
        html = html.replace(
                "<title>Swagger UI</title>",
                "<title>My API Docs</title>");
        html = html.replace(
                "<html lang=\"en\">",
                "<html");
        html = html.replace(
                "</head>",
                CLEAN_CSS + "\n</head>");
        html = html.replace("</body>", buildI18nScript() + "</body>");
        byte[] bytes = html.getBytes(StandardCharsets.UTF_8);
        return new TransformedResource(resource, bytes);
    }

    private String buildI18nScript() {
        return """
                       <script>
                                                                   (function () {
                
                                                                       let currentLang = localStorage.getItem("swagger-lang") || "vi";
                                                                       let translations = {};
                                                                       let debounceTimer = null;
                                                                       async function loadTranslations(lang) {
                                                                           try {
                                                                               const res = await fetch(`/swagger-translations?lang=${lang}`);
                                                                               translations = await res.json();
                                                                           } catch (e) {
                                                                               console.error("Load translation failed", e);
                                                                               translations = {};
                                                                           }
                                                                       }
                
                
                                                                       function translateSwagger() {
                                                                           if (!translations) return;
                
                                                                           const elements = document.querySelectorAll(
                                                                               'span, button, td, th, label, h5, h4, h3, p '
                                                                           );
                
                                                                           elements.forEach(el => {
                                                                               const text = el.innerText?.trim();
                                                                               if (!text) return;
                
                                                                               const translated = translations[text];
                
                                                                               if (translated) {
                                                                                   el.innerText = translated;
                                                                               }
                                                                           });
                                                                       }
                
                                                                       const observer = new MutationObserver(() => {
                                                                           clearTimeout(debounceTimer);
                                                                           debounceTimer = setTimeout(() => {
                                                                               translateSwagger();
                                                                           }, 150);
                                                                       });
                
                                                                       window.switchLang = async function (lang) {
                                                                           localStorage.setItem("swagger-lang", lang);
                
                                                                           await loadTranslations(lang);
                
                                                                           translateSwagger();
                                                                       };
                
                
                                                                       function injectLanguageSwitcher() {
                                                                           const container = document.createElement('div');
                
                                                                           container.style.position = 'fixed';
                                                                           container.style.top = '10px';
                                                                           container.style.right = '20px';
                                                                           container.style.zIndex = 9999;
                                                                           container.style.background = '#fff';
                                                                           container.style.padding = '5px 10px';
                                                                           container.style.borderRadius = '6px';
                                                                           container.style.boxShadow = '0 2px 6px rgba(0,0,0,0.2)';
                
                                                                           const select = document.createElement('select');
                
                                                                           select.innerHTML = `
                                                                               <option value="vi">🇻🇳 VI</option>
                                                                               <option value="en">🇺🇸 EN</option>
                                                                           `;
                
                                                                           select.value = currentLang;
                
                                                                           select.addEventListener('change', (e) => {
                                                                               window.switchLang(e.target.value);
                                                                           });
                
                                                                           container.appendChild(select);
                                                                           document.body.appendChild(container);
                                                                       }
                
                                                                       async function init() {
                                                                           await loadTranslations(currentLang);
                
                                                                           observer.observe(document.body, {
                                                                               childList: true,
                                                                               subtree: true
                                                                           });
                
                                                                           injectLanguageSwitcher();
                                                                           translateSwagger();
                                                                       }
                                                                        const first = document.querySelector('.opblock');
                                                                         if (first) first.click();
                                                                         console.log = () => {};
                                                                         console.debug = () => {};
                                                                         document.body.style.scrollBehavior = "smooth";
                                                                       window.addEventListener("DOMContentLoaded", init);
                
                                                                   })();
                                                                   </script>
                """;
    }

    private static final String CLEAN_CSS =
            "<style>" +
                    ".topbar, .swagger-ui .info { display: none; }" +
                    ".swagger-ui .models { display: none; }" +
                    ".swagger-ui .opblock { margin: 6px 0; border-radius: 8px; box-shadow: none; }" +
                    ".swagger-ui .opblock-summary { padding: 8px 10px; font-size: 13px; }" +
                    "body { background: #fafafa; }" +
                    ".swagger-ui .btn { padding: 4px 8px; font-size: 12px; }" +
                    "</style>";

}
