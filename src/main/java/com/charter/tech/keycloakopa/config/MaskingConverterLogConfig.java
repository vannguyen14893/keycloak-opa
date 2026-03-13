package com.charter.tech.keycloakopa.config;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.HashMap;
import java.util.Map;

public class MaskingConverterLogConfig extends MessageConverter {

    public static final Map<String, String> PII_RULES = new HashMap<>();

    @Override
    public String convert(ILoggingEvent event) {
        String message = super.convert(event);
        return maskSensitiveData(message);
    }

    private String maskSensitiveData(String input) {
        StringBuilder result = new StringBuilder(input.length());
        int i = 0;

        while (i < input.length()) {
            char c = input.charAt(i);
            if (c == '"') {
                int[] out = tryMaskJson(input, i, result);
                if (out != null) {
                    i = out[0];
                    continue;
                }
            }
            int[] out = tryMaskPlainText(input, i, result);
            if (out != null) {
                i = out[0];
                continue;
            }

            result.append(c);
            i++;
        }

        return result.toString();
    }

    private int[] tryMaskJson(String input, int pos, StringBuilder result) {
        int i = pos + 1;
        StringBuilder keyBuf = new StringBuilder();
        while (i < input.length() && input.charAt(i) != '"') {
            keyBuf.append(input.charAt(i));
            i++;
        }
        if (i >= input.length()) return null;
        i++;

        if (!isSensitiveKey(keyBuf.toString())) return null;
        while (i < input.length() && input.charAt(i) == ' ') i++;
        if (i >= input.length() || input.charAt(i) != ':') return null;
        i++;
        while (i < input.length() && input.charAt(i) == ' ') i++;
        if (i >= input.length() || input.charAt(i) != '"') return null;
        i++;
        while (i < input.length() && input.charAt(i) != '"') {
            if (input.charAt(i) == '\\' && i + 1 < input.length()) i++; // escape char
            i++;
        }
        if (i >= input.length()) return null;
        i++;
        result.append('"').append(keyBuf).append('"');
        result.append(':');
        result.append('"');
        result.append(PII_RULES.getOrDefault(keyBuf.toString(), "****"));
        result.append('"');
        return new int[]{i};
    }

    private int[] tryMaskPlainText(String input, int pos, StringBuilder result) {
        String matchedKey = matchSensitiveKey(input, pos);
        if (matchedKey == null) return null;
        int i = pos + matchedKey.length();
        while (i < input.length() && input.charAt(i) == ' ') i++;
        if (i >= input.length() || (input.charAt(i) != '=' && input.charAt(i) != ':')) return null;
        char separator = input.charAt(i);
        i++;
        while (i < input.length() && input.charAt(i) == ' ') i++;
        if (i >= input.length()) return null;
        result.append(matchedKey).append(separator);
        char first = input.charAt(i);
        if (first == '"' || first == '\'') {
            char quote = first;
            result.append(quote);
            i++;
            result.append(PII_RULES.getOrDefault(matchedKey, "****"));
            while (i < input.length() && input.charAt(i) != quote) {
                i++;
            }
        } else {
            result.append(PII_RULES.getOrDefault(matchedKey, "****"));
            while (i < input.length() && !isDelimiter(input.charAt(i))) {
                i++;
            }
        }

        return new int[]{i};
    }

    private String matchSensitiveKey(String input, int pos) {
        for (String key : PII_RULES.keySet()) {
            if (pos + key.length() > input.length()) continue;
            boolean match = true;
            for (int k = 0; k < key.length(); k++) {
                if (Character.toLowerCase(input.charAt(pos + k)) != Character.toLowerCase(key.charAt(k))) {
                    match = false;
                    break;
                }
            }
            if (match) return key;
        }
        return null;
    }

    private boolean isSensitiveKey(String key) {
        return matchSensitiveKey(key, 0) != null && matchSensitiveKey(key, 0).length() == key.length();
    }

    private boolean isDelimiter(char c) {
        return c == ',' || c == ';' || c == ' ' || c == '\n' || c == '\r'
                || c == '}' || c == ')' || c == ']' || c == '&';
    }
}
