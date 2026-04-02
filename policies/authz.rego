package authz

default allow = false
# =============================================================================
# CORE AUTHORIZATION - RBAC
# =============================================================================

# Allow if user has permission via their role
allow if {
    user_has_permission(input.user.role, input.resource, input.action,input.role_permissions)
}

# Helper: Check if role has permission
user_has_permission(role, resource, action,role_permissions) if {
    role_permissions[role][resource][_] == action
}