package authz

default allow = false

role_permissions_test := {
    "ROLE_ADMIN": {
        "products": ["create", "read", "update", "delete", "publish", "unpublish", "archive"],
        "categories": ["create", "read", "update", "delete"],
        "orders": ["create", "read", "update", "delete", "cancel", "refund", "export"],
        "order_status": ["update"],
        "users": ["create", "read", "update", "delete", "suspend", "activate", "assign_role"],
        "inventory": ["create", "read", "update", "delete", "adjust", "transfer", "audit"],
        "promotions": ["create", "read", "update", "delete", "activate", "deactivate"],
        "discounts": ["create", "read", "update", "delete"],
        "reports": ["read", "export", "create"],
        "analytics": ["read", "export"],
        "settings": ["read", "update"],
        "payment_methods": ["read", "update", "configure"],
        "shipping_methods": ["create", "read", "update", "delete"],
        "reviews": ["read", "moderate", "delete"],
        "support_tickets": ["read", "update", "assign", "close"],
        "notifications": ["create", "send"],
        "audit_logs": ["read", "export"]
    },
    "ROLE_STORE_MANAGER": {
        "products": ["create", "read", "update", "publish", "unpublish"],
        "categories": ["create", "read", "update"],
        "orders": ["read", "update", "cancel", "export"],
        "order_status": ["update"],
        "users": ["read"],
        "inventory": ["create", "read", "update", "adjust", "transfer"],
        "promotions": ["create", "read", "update", "activate"],
        "discounts": ["create", "read", "update"],
        "reports": ["read", "export"],
        "analytics": ["read"],
        "reviews": ["read", "moderate"],
        "support_tickets": ["read", "update"],
        "notifications": ["create"]
    },
    "ROLE_CUSTOMER_SERVICE": {
        "products": ["read"],
        "categories": ["read"],
        "orders": ["read", "update", "cancel", "refund"],
        "order_status": ["update"],
        "users": ["read", "update"],
        "inventory": ["read"],
        "promotions": ["read"],
        "discounts": ["read"],
        "reviews": ["read", "moderate"],
        "support_tickets": ["create", "read", "update", "assign", "close"],
        "notifications": ["create", "send"]
    },
    "ROLE_INVENTORY_MANAGER": {
        "products": ["read", "update"],
        "categories": ["read"],
        "orders": ["read"],
        "inventory": ["create", "read", "update", "adjust", "transfer", "audit"],
        "reports": ["read"],
        "suppliers": ["create", "read", "update"],
        "warehouses": ["read", "update"]
    },
    "ROLE_CUSTOMER": {
        "products": ["read"],
        "categories": ["read"],
        "orders": ["create", "read", "cancel"],
        "cart": ["create", "read", "update", "delete"],
        "wishlist": ["create", "read", "update", "delete"],
        "profile": ["read", "update"],
        "addresses": ["create", "read", "update", "delete"],
        "payment_methods": ["create", "read", "update", "delete"],
        "reviews": ["create", "read", "update", "delete"],
        "support_tickets": ["create", "read", "update"]
    },
    "ROLE_GUEST": {
        "products": ["read"],
        "categories": ["read"],
        "cart": ["create", "read", "update", "delete"]
    }
}
# =============================================================================
# CORE AUTHORIZATION - RBAC
# =============================================================================

# Allow if user has permission via their role
allow if {
    user_has_permission(input.user.role, input.resource, input.action)
}

# Helper: Check if role has permission
user_has_permission(role, resource, action,role_permissions) if {
    role_permissions[role][resource][_] == action
}