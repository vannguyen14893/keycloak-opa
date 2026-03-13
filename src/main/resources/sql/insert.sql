--
-- TOC entry 3503 (class 0 OID 35283)
-- Dependencies: 234
-- Data for Name: error_catalog; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.error_catalog VALUES (7, 'BIZ_USER_001', 400, 'data.not.found', 'USER-SERVICE', 'BUSINESS', false, 'WARN');
INSERT INTO public.error_catalog VALUES (2, 'VAL_COMMON_PARAM_TYPE', 400, 'invalid.param.type', 'USER-SERVICE', 'VALIDATION', false, 'WARN');
INSERT INTO public.error_catalog VALUES (3, 'VAL_COMMON_METHOD_NOT_ALLOWED', 405, 'http.method.not.supported', 'USER-SERVICE', 'VALIDATION', false, 'WARN');
INSERT INTO public.error_catalog VALUES (4, 'VAL_COMMON_ENDPOINT_NOT_FOUND', 404, 'endpoint.not.found', 'USER-SERVICE', 'VALIDATION', false, 'WARN');
INSERT INTO public.error_catalog VALUES (5, 'VAL_COMMON_MEDIA_TYPE_NOT_SUPPORTED', 415, 'media.type.not.supported', 'USER-SERVICE', 'VALIDATION', false, 'WARN');
INSERT INTO public.error_catalog VALUES (6, 'SYS_COMMON_001', 500, 'system.error', 'USER-SERVICE', 'SYSTEMS', false, 'ERROR');
INSERT INTO public.error_catalog VALUES (9, 'SEC_ROLE_REQUIRED', 403, 'sec.role.required', 'USER-SERVICE', 'SECURITY', false, 'WARN');
INSERT INTO public.error_catalog VALUES (10, 'SEC_AUTH_REQUIRED', 401, 'sec.auth.required', 'USER-SERVICE', 'SECURITY', false, 'WARN');
INSERT INTO public.error_catalog VALUES (11, 'SEC_AUTH_INVALID_TOKEN', 401, 'sec.auth.token.invalid', 'USER-SERVICE', 'SECURITY', false, 'WARN');
INSERT INTO public.error_catalog VALUES (8, 'SEC_PERMISSION_DENIED', 403, 'sec.permission.denied', 'USER-SERVICE', 'SECURITY', false, 'WARN');
INSERT INTO public.error_catalog VALUES (1, 'VAL_USER_001', 400, 'validation.error', 'USER-SERVICE', 'VALIDATION', false, 'WARN');


--
-- TOC entry 3499 (class 0 OID 27099)
-- Dependencies: 230
-- Data for Name: languages; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.languages VALUES (1, 'vn', 'Việt Nam', true);
INSERT INTO public.languages VALUES (2, 'en', 'English', true);


--
-- TOC entry 3501 (class 0 OID 27107)
-- Dependencies: 232
-- Data for Name: languages_detail; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.languages_detail VALUES (33, NULL, 'sec.auth.token.invalid', 'Token invalid', 2, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (34, NULL, 'sec.auth.token.invalid', 'Mã xác không hợp lệ', 1, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (1, NULL, 'password.invalid', 'Mật khẩu {0} không hợp lệ', 1, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (2, NULL, 'password.invalid', 'Password {0} invalid', 2, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (3, NULL, 'message.success', 'Successfully', 2, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (4, NULL, 'message.success', 'Thành công', 1, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (5, NULL, 'username.required', 'Username is required', 2, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (6, NULL, 'username.required', 'Tên người dùng là bắt buộc', 1, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (7, NULL, 'password.required', 'Password is required', 2, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (8, NULL, 'password.required', 'Mật khẩu là bắt buộc', 1, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (9, NULL, 'validation.error', 'Validation error', 2, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (10, NULL, 'validation.error', 'Lỗi xác thực', 1, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (11, NULL, 'invalid.param.type', 'Invalid parameter type', 2, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (12, NULL, 'invalid.param.type', 'Loại tham số không hợp lệ', 1, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (13, NULL, 'http.method.not.supported', 'HTTP method not supported', 2, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (14, NULL, 'http.method.not.supported', 'Phương thức HTTP không được hỗ trợ', 1, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (15, NULL, 'endpoint.not.found', 'Endpoint not found', 2, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (16, NULL, 'endpoint.not.found', 'Không tìm thấy điểm cuối.', 1, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (17, NULL, 'media.type.not.supported', 'Unsupported content type', 2, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (18, NULL, 'media.type.not.supported', 'Loại nội dung không được hỗ trợ', 1, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (19, NULL, 'system.error', 'Systems errors', 2, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (20, NULL, 'system.error', 'Hệ thống xảy ra lỗi', 1, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (21, NULL, 'data.not.found', 'Data not found', 2, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (22, NULL, 'data.not.found', 'Dữ liệu không tồn tại', 1, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (23, NULL, 'system.key.message.not.found', 'Key message not found', 2, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (24, NULL, 'system.key.message.not.found', 'Không tìm thấy key message', 1, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (25, NULL, 'user.not.found', 'User with id {0} not found', 2, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (26, NULL, 'user.not.found', 'Tên người dùng với mã {0} không tồn tại', 1, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (27, NULL, 'sec.permission.denied', 'Bạn không có quyền truy cập chức năng này', 1, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (28, NULL, 'sec.permission.denied', 'You do not have permission to access this function', 2, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (29, NULL, 'sec.role.required', 'Access denied. Required role is missing.', 2, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (30, NULL, 'sec.role.required', 'Quyền truy cập bị từ chối. Thiếu vai trò cần thiết.', 1, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (31, NULL, 'sec.auth.required', 'Authentication is required to access this resource', 2, 'USER-SERVICE');
INSERT INTO public.languages_detail VALUES (32, NULL, 'sec.auth.required', 'Cần phải xác thực để truy cập tài nguyên này', 1, 'USER-SERVICE');


--
-- TOC entry 3493 (class 0 OID 19178)
-- Dependencies: 224
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.orders VALUES (1, 'B001', 'O01', 'U01', 'NEW', 2000000.00);
INSERT INTO public.orders VALUES (2, 'B001', 'O02', 'U01', 'NEW', 15000000.00);
INSERT INTO public.orders VALUES (3, 'B001', 'O03', 'U02', 'COMPLETED', 8000000.00);


--
-- TOC entry 3484 (class 0 OID 19129)
-- Dependencies: 215
-- Data for Name: permission_scopes; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.permission_scopes VALUES (1, 1, 1, 1, NULL);
INSERT INTO public.permission_scopes VALUES (2, 2, 1, 1, NULL);
INSERT INTO public.permission_scopes VALUES (3, 3, 1, 1, NULL);
INSERT INTO public.permission_scopes VALUES (4, 4, 1, 1, NULL);
INSERT INTO public.permission_scopes VALUES (5, 5, 1, 1, NULL);
INSERT INTO public.permission_scopes VALUES (6, 6, 1, 1, NULL);
INSERT INTO public.permission_scopes VALUES (7, 7, 1, 1, NULL);
INSERT INTO public.permission_scopes VALUES (8, 8, 1, 1, NULL);
INSERT INTO public.permission_scopes VALUES (9, 9, 1, 1, NULL);
INSERT INTO public.permission_scopes VALUES (10, 1, 3, 2, NULL);
INSERT INTO public.permission_scopes VALUES (11, 2, 3, 3, NULL);
INSERT INTO public.permission_scopes VALUES (12, 3, 3, 3, 'NEW');
INSERT INTO public.permission_scopes VALUES (13, 2, 2, 2, NULL);
INSERT INTO public.permission_scopes VALUES (14, 3, 2, 5, 'NEW');
INSERT INTO public.permission_scopes VALUES (15, 4, 2, 4, '10000000');
INSERT INTO public.permission_scopes VALUES (16, 8, 2, 2, NULL);
INSERT INTO public.permission_scopes VALUES (17, 4, 5, 4, '50000000');
INSERT INTO public.permission_scopes VALUES (18, 7, 5, 2, NULL);
INSERT INTO public.permission_scopes VALUES (19, 8, 5, 2, NULL);
INSERT INTO public.permission_scopes VALUES (20, 2, 6, 2, NULL);
INSERT INTO public.permission_scopes VALUES (21, 7, 6, 2, NULL);
INSERT INTO public.permission_scopes VALUES (22, 8, 6, 2, NULL);


--
-- TOC entry 3486 (class 0 OID 19135)
-- Dependencies: 217
-- Data for Name: permissions; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.permissions VALUES (1, 'create', 'ORDER_CREATE', 'order');
INSERT INTO public.permissions VALUES (2, 'view', 'ORDER_VIEW', 'order');
INSERT INTO public.permissions VALUES (3, 'cancel', 'ORDER_CANCEL', 'order');
INSERT INTO public.permissions VALUES (4, 'refund', 'ORDER_REFUND', 'order');
INSERT INTO public.permissions VALUES (5, 'manage', 'PRODUCT_MANAGE', 'product');
INSERT INTO public.permissions VALUES (6, 'update', 'INVENTORY_UPDATE', 'inventory');
INSERT INTO public.permissions VALUES (7, 'view', 'PAYMENT_VIEW', 'payment');
INSERT INTO public.permissions VALUES (8, 'view', 'REPORT_VIEW', 'report');
INSERT INTO public.permissions VALUES (9, 'manage', 'USER_MANAGE', 'user');


--
-- TOC entry 3497 (class 0 OID 27091)
-- Dependencies: 228
-- Data for Name: pii_rule; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.pii_rule VALUES (1, 'cif', '******', true);
INSERT INTO public.pii_rule VALUES (2, 'email', '***@***.***', true);
INSERT INTO public.pii_rule VALUES (3, 'password', '******', true);
INSERT INTO public.pii_rule VALUES (4, 'cardNumber', '****-****-****-****', true);
INSERT INTO public.pii_rule VALUES (5, 'phone', '**** *** ***', true);


--
-- TOC entry 3491 (class 0 OID 19162)
-- Dependencies: 222
-- Data for Name: role_permissions; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.role_permissions VALUES (1, 1);
INSERT INTO public.role_permissions VALUES (1, 2);
INSERT INTO public.role_permissions VALUES (1, 3);
INSERT INTO public.role_permissions VALUES (1, 4);
INSERT INTO public.role_permissions VALUES (1, 5);
INSERT INTO public.role_permissions VALUES (1, 6);
INSERT INTO public.role_permissions VALUES (1, 7);
INSERT INTO public.role_permissions VALUES (1, 8);
INSERT INTO public.role_permissions VALUES (1, 9);
INSERT INTO public.role_permissions VALUES (2, 2);
INSERT INTO public.role_permissions VALUES (2, 3);
INSERT INTO public.role_permissions VALUES (2, 4);
INSERT INTO public.role_permissions VALUES (2, 5);
INSERT INTO public.role_permissions VALUES (2, 8);
INSERT INTO public.role_permissions VALUES (3, 1);
INSERT INTO public.role_permissions VALUES (3, 2);
INSERT INTO public.role_permissions VALUES (3, 3);
INSERT INTO public.role_permissions VALUES (3, 7);
INSERT INTO public.role_permissions VALUES (4, 2);
INSERT INTO public.role_permissions VALUES (4, 6);
INSERT INTO public.role_permissions VALUES (5, 4);
INSERT INTO public.role_permissions VALUES (5, 7);
INSERT INTO public.role_permissions VALUES (5, 8);
INSERT INTO public.role_permissions VALUES (6, 2);
INSERT INTO public.role_permissions VALUES (6, 7);
INSERT INTO public.role_permissions VALUES (6, 8);


--
-- TOC entry 3488 (class 0 OID 19143)
-- Dependencies: 219
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.roles VALUES (1, 'ROLE_ADMIN', 'System Admin');
INSERT INTO public.roles VALUES (2, 'ROLE_STORE_MANAGER', 'Store Manager');
INSERT INTO public.roles VALUES (3, 'ROLE_SALE_STAFF', 'Sale Staff');
INSERT INTO public.roles VALUES (4, 'ROLE_WAREHOUSE', 'Warehouse Staff');
INSERT INTO public.roles VALUES (5, 'ROLE_ACCOUNTANT', 'Accountant');
INSERT INTO public.roles VALUES (6, 'ROLE_AUDITOR', 'Auditor');


--
-- TOC entry 3490 (class 0 OID 19149)
-- Dependencies: 221
-- Data for Name: scopes; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.scopes VALUES (1, '*', 'any', 'No restriction');
INSERT INTO public.scopes VALUES (2, 'branch_id', 'branch', 'Same branch');
INSERT INTO public.scopes VALUES (3, 'owner_id', 'own', 'Own data only');
INSERT INTO public.scopes VALUES (4, 'amount', 'amount', 'Amount limitation');
INSERT INTO public.scopes VALUES (5, 'status', 'status', 'Resource status');


--
-- TOC entry 3495 (class 0 OID 19186)
-- Dependencies: 226
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.users VALUES (1, 'B001', 'sale_01', 3);
INSERT INTO public.users VALUES (2, 'B001', 'manager_01', 2);
INSERT INTO public.users VALUES (3, 'B001', 'accountant_01', 5);
INSERT INTO public.users VALUES (4, 'B001', 'auditor_01', 6);
INSERT INTO public.users VALUES (5, 'HQ', 'admin', 1);