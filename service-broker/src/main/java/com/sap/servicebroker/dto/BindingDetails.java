package com.sap.servicebroker.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class BindingDetails {

    private final String clientId;
    private final String secret;
    private final String oAuthServiceUrl;

    @JsonCreator
    public BindingDetails( @JsonProperty("clientid") String clientId, //
                           @JsonProperty("clientsecret") String secret, //
                           @JsonProperty("url") String oAuthServiceUrl //
    ) {
        this.clientId = clientId;
        this.secret = secret;
        this.oAuthServiceUrl = oAuthServiceUrl;
    }

    @JsonProperty("clientid")
    public String getClientId() {
        return clientId;
    }

    @JsonProperty("clientsecret")
    public String getSecret() {
        return secret;
    }

    @JsonProperty("url")
    public String getOAuthServiceUrl() {
        return oAuthServiceUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BindingDetails that = (BindingDetails) o;
        return Objects.equals(clientId, that.clientId) && Objects.equals(secret, that.secret) && Objects
                .equals(oAuthServiceUrl, that.oAuthServiceUrl);
    }

    @Override
    public int hashCode() {

        return Objects.hash(clientId, secret, oAuthServiceUrl);
    }
}
