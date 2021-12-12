package com.sap.servicebroker.dto;

import com.sap.servicebroker.enums.OAuthScope;

import java.util.Collection;

public class OAuth2CredentialsBuilder {

    private String clientId;
    private String clientSecret;
    private String oAuthServiceUrl;
    private Collection<OAuthScope> scopes;

    public OAuth2CredentialsBuilder withClientId( String clientId ) {
        this.clientId = clientId;
        return this;
    }

    public OAuth2CredentialsBuilder withClientSecret( String clientSecret ) {
        this.clientSecret = clientSecret;
        return this;
    }

    public OAuth2CredentialsBuilder withOAuthServiceURL( String oAuthServiceUrl ) {
        this.oAuthServiceUrl = oAuthServiceUrl;
        return this;
    }

    public OAuth2CredentialsBuilder withOAuthScopes( Collection<OAuthScope> scopes ) {
        this.scopes = scopes;
        return this;
    }

    public OAuth2Credentials build() {
        return new OAuth2Credentials(clientId, clientSecret, oAuthServiceUrl, scopes);
    }

}
