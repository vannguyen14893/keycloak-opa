package com.charter.tech.keycloakopa.config;

import com.charter.tech.keycloakopa.constans.MessageConstants;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Configuration class for handling message source in the application.
 * Extends AbstractMessageSource to provide custom message resolution
 * from database or other sources based on locale settings.
 */
@Component("messageSource")
public class DBMessageSourceConfig extends AbstractMessageSource {
    public static final Map<String, Map<String, String>> LANGUAGES = new HashMap<>();

    /**
     * Resolves message codes to their corresponding MessageFormat.
     * Falls back to "error" if no message is found for the given code.
     *
     * @param s      the code to lookup up, such as 'calculator.noRateFound'
     * @param locale the Locale to use for message resolution
     * @return the MessageFormat for the message
     */
    @Override
    protected MessageFormat resolveCode(String s, Locale locale) {
        String country = locale.getLanguage();
        Map<String, String> languagesDetails = LANGUAGES.get(country);
        if (languagesDetails == null) {
            languagesDetails = LANGUAGES.get(Locale.ENGLISH.getLanguage());
        }
        String message = languagesDetails.get(s);
        if (message == null) {
            message = languagesDetails.get(MessageConstants.SYS_KEY_MESSAGE_NOT_FOUND);
        }
        return new MessageFormat(message, locale);
    }

    /**
     * Retrieves a message for the specified key using English locale.
     *
     * @param key the message key to lookup
     * @return the message in English locale
     */
    public String getMessages(String key, Object[] arg, Locale locale) {
        return getMessage(key, arg, locale);
    }
}
