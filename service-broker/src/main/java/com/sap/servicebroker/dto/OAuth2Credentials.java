package com.sap.servicebroker.dto;

import com.sap.servicebroker.enums.OAuthScope;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static java.util.Collections.unmodifiableCollection;

public class OAuth2Credentials {

    private final String clientId;
    private final String clientSecret;
    private final String oAuthServiceUrl;
    private final Collection<OAuthScope> scopes;

    public OAuth2Credentials(String clientId) {
        this(clientId, null, null, null);
    }

    public OAuth2Credentials(String clientId, String clientSecret, String oAuthServiceUrl, Collection<OAuthScope> scopes) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.oAuthServiceUrl = oAuthServiceUrl;
        this.scopes = new ArrayList<>(CollectionUtils.emptyIfNull(scopes));
    }

    public static OAuth2CredentialsBuilder builder() {
        return new OAuth2CredentialsBuilder();
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getOAuthServiceUrl() {
        return oAuthServiceUrl;
    }

    public Collection<OAuthScope> getScopes() {
        return unmodifiableCollection(scopes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OAuth2Credentials that = (OAuth2Credentials) o;
        return Objects.equals(clientId, that.clientId) && Objects.equals(clientSecret, that.clientSecret)
                && Objects.equals(oAuthServiceUrl, that.oAuthServiceUrl) && Objects.equals(scopes, that.scopes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, clientSecret, oAuthServiceUrl, scopes);
    }
}
