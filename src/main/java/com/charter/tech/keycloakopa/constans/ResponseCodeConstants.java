package com.charter.tech.keycloakopa.constans;

public class ResponseCodeConstants {
    public static final String CODE_SUCCESS = "00";
    //System error
    public static final String SYS_COMMON_001 = "SYS_COMMON_001";
    //Business error
    public static final String BIZ_USER_001 = "BIZ_USER_001";
    //Validation error
    public static final String VAL_COMMON_PARAM_TYPE = "VAL_COMMON_PARAM_TYPE";
    public static final String VAL_COMMON_METHOD_NOT_ALLOWED = "VAL_COMMON_METHOD_NOT_ALLOWED";
    public static final String VAL_COMMON_ENDPOINT_NOT_FOUND = "VAL_COMMON_ENDPOINT_NOT_FOUND";
    public static final String VAL_COMMON_MEDIA_TYPE_NOT_SUPPORTED = "VAL_COMMON_MEDIA_TYPE_NOT_SUPPORTED";
    //Security error
    public static final String SEC_AUTH_INVALID_TOKEN = "SEC_AUTH_INVALID_TOKEN";
    public static final String SEC_AUTH_TOKEN_EXPIRED = "SEC_AUTH_TOKEN_EXPIRED";
    public static final String SEC_AUTH_BAD_CREDENTIALS = "SEC_AUTH_BAD_CREDENTIALS";
    public static final String SEC_AUTH_REQUIRED = "SEC_AUTH_REQUIRED";
    public static final String SEC_ACCESS_DENIED = "SEC_ACCESS_DENIED";
    public static final String SEC_PERMISSION_DENIED = "SEC_PERMISSION_DENIED";
    public static final String SEC_ROLE_REQUIRED = "SEC_ROLE_REQUIRED";
    public static final String SEC_CSRF_INVALID = "SEC_CSRF_INVALID";
    public static final String SEC_CORS_BLOCKED = "SEC_CORS_BLOCKED";
    public static final String SEC_RATE_LIMIT_EXCEEDED = "SEC_RATE_LIMIT_EXCEEDED";
    public static final String SEC_IP_BLOCKED = "SEC_IP_BLOCKED";

    //External service error
    public static final String EXT_001 = "EXT_001";
}
