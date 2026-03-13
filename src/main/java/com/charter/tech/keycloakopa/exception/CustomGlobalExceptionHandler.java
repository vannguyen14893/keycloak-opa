package com.charter.tech.keycloakopa.exception;

import com.charter.tech.keycloakopa.config.DBMessageSourceConfig;
import com.charter.tech.keycloakopa.constans.LogAttributeConstants;
import com.charter.tech.keycloakopa.constans.ResponseCodeConstants;
import com.charter.tech.keycloakopa.dto.ErrorResultResponse;
import com.charter.tech.keycloakopa.dto.SuccessResultResponse;
import com.charter.tech.keycloakopa.entity.ErrorCatalog;
import com.charter.tech.keycloakopa.service.ErrorCatalogService;
import com.charter.tech.keycloakopa.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class CustomGlobalExceptionHandler {

    private final DBMessageSourceConfig dbMessageSourceConfig;

    private final ErrorCatalogService errorCatalogService;

    private final Utils utils;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResultResponse<Map<String, Object>>> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, Locale locale) {
        Map<String, Object> errors = new HashMap<>();
        Set<String> fields = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getField).collect(Collectors.toSet());
        for (String filed : fields) {
            Set<String> errorsDetails = new HashSet<>();
            for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
                if (filed.equals(error.getField())) {
                    errorsDetails.add(error.getDefaultMessage());
                }
            }
            errors.put(filed, errorsDetails);
        }
        String errorCode = utils.getModuleName(Objects.requireNonNull(ex.getBindingResult().getTarget()));
        Map<String, ErrorCatalog> errorCatalogMap = errorCatalogService.findAllByService();
        ErrorCatalog errorCatalog = errorCatalogMap.get(errorCode);
        String messages = dbMessageSourceConfig.getMessages(errorCatalog.getMessageKey(), null, locale);
        return new ResponseEntity<>(new ErrorResultResponse<>(utils.getValueLog(LogAttributeConstants.TRACE_ID), errorCode, messages,errorCatalog.getSeverity(), errorCatalog.getRetryable(), errors), HttpStatusCode.valueOf(errorCatalog.getHttpStatus()));
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResultResponse<Map<String, String>>> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, Locale locale) {
        String error = ex.getName() + " should be of type " + ex.getRequiredType();
        Map<String, ErrorCatalog> errorCatalogMap = errorCatalogService.findAllByService();
        ErrorCatalog errorCatalog = errorCatalogMap.get(ResponseCodeConstants.VAL_COMMON_PARAM_TYPE);
        String messages = dbMessageSourceConfig.getMessages(errorCatalog.getMessageKey(), null, locale);
        return new ResponseEntity<>(new ErrorResultResponse<>(utils.getValueLog(LogAttributeConstants.TRACE_ID), ResponseCodeConstants.VAL_COMMON_PARAM_TYPE, messages, errorCatalog.getSeverity(), errorCatalog.getRetryable(), Map.of("error", error)), HttpStatusCode.valueOf(errorCatalog.getHttpStatus()));
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ErrorResultResponse<Map<String, String>>> handleMethodNotSupportedException(final HttpRequestMethodNotSupportedException ex, Locale locale) {
        Map<String, ErrorCatalog> errorCatalogMap = errorCatalogService.findAllByService();
        ErrorCatalog errorCatalog = errorCatalogMap.get(ResponseCodeConstants.VAL_COMMON_METHOD_NOT_ALLOWED);
        String messages = dbMessageSourceConfig.getMessages(errorCatalog.getMessageKey(), null, locale);
        return new ResponseEntity<>(new ErrorResultResponse<>(utils.getValueLog(LogAttributeConstants.TRACE_ID), ResponseCodeConstants.VAL_COMMON_METHOD_NOT_ALLOWED, messages, errorCatalog.getSeverity(), errorCatalog.getRetryable(), Map.of("error", ex.getMessage())), HttpStatusCode.valueOf(errorCatalog.getHttpStatus()));
    }

    @ExceptionHandler({NoResourceFoundException.class})
    public ResponseEntity<ErrorResultResponse<Map<String, String>>> handleNoHandlerFoundException(final NoResourceFoundException ex, Locale locale) {
        Map<String, ErrorCatalog> errorCatalogMap = errorCatalogService.findAllByService();
        ErrorCatalog errorCatalog = errorCatalogMap.get(ResponseCodeConstants.VAL_COMMON_ENDPOINT_NOT_FOUND);
        String messages = dbMessageSourceConfig.getMessages(errorCatalog.getMessageKey(), null, locale);
        return new ResponseEntity<>(new ErrorResultResponse<>(utils.getValueLog(LogAttributeConstants.TRACE_ID), ResponseCodeConstants.VAL_COMMON_ENDPOINT_NOT_FOUND, messages, errorCatalog.getSeverity(), errorCatalog.getRetryable(), Map.of("error", ex.getMessage())), HttpStatusCode.valueOf(errorCatalog.getHttpStatus()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResultResponse<Map<String, String>>> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, Locale locale) {
        Map<String, ErrorCatalog> errorCatalogMap = errorCatalogService.findAllByService();
        ErrorCatalog errorCatalog = errorCatalogMap.get(ResponseCodeConstants.VAL_COMMON_MEDIA_TYPE_NOT_SUPPORTED);
        String messages = dbMessageSourceConfig.getMessages(errorCatalog.getMessageKey(), null, locale);
        return new ResponseEntity<>(new ErrorResultResponse<>(utils.getValueLog(LogAttributeConstants.TRACE_ID), ResponseCodeConstants.VAL_COMMON_MEDIA_TYPE_NOT_SUPPORTED, messages, errorCatalog.getSeverity(), errorCatalog.getRetryable(), Map.of("error", ex.getMessage())), HttpStatusCode.valueOf(errorCatalog.getHttpStatus()));
    }

    @ExceptionHandler(CustomAccessDeniedException.class)
    public ResponseEntity<ErrorResultResponse<Map<String, String>>> handleAccessDenied(CustomAccessDeniedException ex, Locale locale) {
        Map<String, ErrorCatalog> errorCatalogMap = errorCatalogService.findAllByService();
        ErrorCatalog errorCatalog = errorCatalogMap.get(ex.getMessage());
        String messages = dbMessageSourceConfig.getMessages(errorCatalog.getMessageKey(), null, locale);
        return new ResponseEntity<>(new ErrorResultResponse<>(utils.getValueLog(LogAttributeConstants.TRACE_ID), ex.getMessage(), messages, errorCatalog.getSeverity(), errorCatalog.getRetryable(), Map.of("error", messages)), HttpStatusCode.valueOf(errorCatalog.getHttpStatus()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResultResponse<Map<String, String>>> handlerException(final Exception ex, Locale locale) {
        Map<String, ErrorCatalog> errorCatalogMap = errorCatalogService.findAllByService();
        ErrorCatalog errorCatalog = errorCatalogMap.get(ResponseCodeConstants.SYS_COMMON_001);
        String messages = dbMessageSourceConfig.getMessages(errorCatalog.getMessageKey(), null, locale);
        return new ResponseEntity<>(new ErrorResultResponse<>(utils.getValueLog(LogAttributeConstants.TRACE_ID), ResponseCodeConstants.SYS_COMMON_001, messages, errorCatalog.getSeverity(), errorCatalog.getRetryable(), Map.of("error", ex.getMessage())), HttpStatusCode.valueOf(errorCatalog.getHttpStatus()));
    }


    @ExceptionHandler({BusinessExceptionHandler.class})
    public ResponseEntity<SuccessResultResponse<Map<String, String>>> businessExceptionHandler(final BusinessExceptionHandler ex, Locale locale) {
        Map<String, ErrorCatalog> errorCatalogMap = errorCatalogService.findAllByService();
        ErrorCatalog errorCatalog = errorCatalogMap.get(ex.getCode());
        String messages = dbMessageSourceConfig.getMessages(errorCatalog.getMessageKey(), null, locale);
        return new ResponseEntity<>(new SuccessResultResponse<>(utils.getValueLog(LogAttributeConstants.TRACE_ID), ex.getCode(), messages, Map.of("error", ex.getMessage())), HttpStatusCode.valueOf(errorCatalog.getHttpStatus()));
    }
}
