package com.sap.servicebroker.enums;

import antlr.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

public enum OAuthScope {

    ADMIN_USER("admin"),
    NORMAL_USER("normal_user");

    private static final Map<String, OAuthScope> KEY_TO_SCOPE_MAP;

    static {
        Map<String, OAuthScope> result = new HashMap<>();
        for (OAuthScope scope : OAuthScope.values()) {
            result.put(scope.getKey(), scope);
        }

        KEY_TO_SCOPE_MAP = unmodifiableMap(result);
    }

    private String key;

    OAuthScope(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static String toXsUaaScope(String xsappname, OAuthScope scope) {
        return String.format("%s.%s", xsappname, scope.getKey());
    }

    public static OAuthScope fromXsUaaScope(String xsappname, String scope) {
        return KEY_TO_SCOPE_MAP.get(StringUtils.stripFront(scope, String.format("%s.", xsappname)));
    }

}
