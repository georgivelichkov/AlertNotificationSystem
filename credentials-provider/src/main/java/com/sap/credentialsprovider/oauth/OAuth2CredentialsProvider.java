package com.sap.credentialsprovider.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.credentialsprovider.ICredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Objects;

import static java.lang.System.currentTimeMillis;
import static org.apache.commons.codec.Charsets.UTF_8;
import static org.apache.commons.codec.binary.Base64.encodeBase64String;

public class OAuth2CredentialsProvider implements ICredentialsProvider {

    private static final ObjectMapper JSON_OBJECT_MAPPER = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(OAuth2CredentialsProvider.class);
    private final RestTemplate restTemplate;
    private final URI uaaAPIURI;
    private final String basicAuthenticationString;

    private String oAuthToken;
    private long tokenExpirationTimestamp;

    public OAuth2CredentialsProvider(String clientId, //
                                     String clientSecret, //
                                     String uaaAPIURI,
                                     RestTemplate restTemplate) {
        this.uaaAPIURI = toURI(buildTokenURL(uaaAPIURI));
        this.restTemplate = restTemplate;
        this.basicAuthenticationString = buildBasicAuthenticationString(clientId, clientSecret);
    }

    private String buildBasicAuthenticationString(String username, String password) {
        byte[] basicAuth = String.format("Basic %s", encodeBase64String(String.format("%s:%s", username, password).getBytes(UTF_8)).trim()).getBytes(UTF_8);
        return new String(basicAuth);
    }

    private URI toURI(String uaaAPIURL) {
        try {
            return URI.create(uaaAPIURL);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Could not build uaa server api URI", e);
        }
    }

    private String buildTokenURL(String uaaAPIURL) {
        return String.format(uaaAPIURL+"%s", "/oauth/token?grant_type=client_credentials");
    }

    @Override
    public long getExpirationTime() {
        return tokenExpirationTimestamp;
    }

    @Override
    public synchronized String getCredentialsAsString() {

        if(StringUtils.isEmpty(oAuthToken) || isTokenAboutToExpire()) {
            renewToken();
        }

        return String.format("Bearer %s", oAuthToken);
    }

    private boolean isTokenAboutToExpire() {
        return currentTimeMillis() > tokenExpirationTimestamp;
    }

    private void renewToken() {
        try {
            LOG.info(uaaAPIURI.toString());
            LOG.info(Objects.requireNonNull(getEntity().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0));
            ResponseEntity<String> response = restTemplate.exchange(uaaAPIURI, HttpMethod.POST, getEntity(), String.class);

            JsonNode responseBody = JSON_OBJECT_MAPPER.readValue(response.getBody(), JsonNode.class);
            oAuthToken = responseBody.get("access_token").asText();
            tokenExpirationTimestamp = currentTimeMillis() + (responseBody.get("expires_in").asLong() * 1000) - 60000L;
        } catch (Exception e) {
            throw new RuntimeException("Could not obtain access token", e);
        }
    }

    private HttpEntity<?> getEntity() {
        return new HttpEntity<>(getHttpHeaders());
    }


    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, basicAuthenticationString);

        return headers;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        OAuth2CredentialsProvider that = (OAuth2CredentialsProvider) object;
        return tokenExpirationTimestamp == that.tokenExpirationTimestamp && Objects.equals(uaaAPIURI, that.uaaAPIURI)
                && Objects.equals(basicAuthenticationString, that.basicAuthenticationString) && Objects.equals(oAuthToken, that.oAuthToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uaaAPIURI, restTemplate, basicAuthenticationString, oAuthToken, tokenExpirationTimestamp);
    }
}
