package com.sap.servicebroker.utils;

import com.sap.credentialsprovider.oauth.OAuth2CredentialsProvider;
import com.sap.servicebroker.dto.BindingDetails;
import com.sap.servicebroker.dto.IdentityDetails;
import com.sap.servicebroker.exceptions.OAuthIdentityProviderServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpMethod.*;

public class HTTPUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HTTPUtils.class);
    private static final String XSUAA_IDENTITY_CLONE_URL_TEMPLATE = "%s/sap/rest/broker/clones/%s";
    private static final String XSUAA_IDENTITY_CLONE_BINDING_DETAILS_URL_TEMPLATE = "%s/sap/rest/broker/clones/%s/binding";
    private final RestTemplate restTemplate;
    private final OAuth2CredentialsProvider credentialsProvider;
    private final String xsUaaBaseUrl;

    @Value("xsuaa.orgGuid")
    private String orgGuid;

    public HTTPUtils(RestTemplate restTemplate, OAuth2CredentialsProvider credentialsProvider, String xsUaaBaseUrl) {
        this.restTemplate = restTemplate;
        this.credentialsProvider = credentialsProvider;
        this.xsUaaBaseUrl = xsUaaBaseUrl;
    }


    public BindingDetails retrieveBindingDetails(String instanceId) throws OAuthIdentityProviderServiceException {
        try {
            LOGGER.info("Retrieving Binding Details");
            LOGGER.info(String.format("URL: %s", getUriForBindingDetails(instanceId)));
            return restTemplate.exchange(getUriForBindingDetails(instanceId), GET, getEntity(), BindingDetails.class).getBody();
        } catch (HttpStatusCodeException e) {
            LOGGER.error("Error in retrieving Binding Details");
            throw new OAuthIdentityProviderServiceException(e.getMessage());
        }
    }


    public IdentityDetails retrieveIdentityDetails(String instanceId) throws OAuthIdentityProviderServiceException {
        try {
            LOGGER.info("Retrieving Identity Details");
            LOGGER.info(String.format("URL: %s", getUriForCloneDetails(instanceId)));
            return restTemplate.exchange(getUriForCloneDetails(instanceId), GET, getEntity(), IdentityDetails.class).getBody();
        } catch (HttpStatusCodeException e) {
            LOGGER.error("Error in retrieving Identity Details");
            throw new OAuthIdentityProviderServiceException(e.getMessage());
        }
    }

    public IdentityDetails createIdentityDetails(String instanceId) throws OAuthIdentityProviderServiceException {
        try {
            LOGGER.info("Creating Identity Details");
            LOGGER.info(String.format("URL: %s", getUriForCloneDetails(instanceId)));
            return restTemplate.exchange(getUriForCreationCloneDetails(instanceId), POST, getCreationEntity(instanceId), IdentityDetails.class).getBody();
        } catch (HttpStatusCodeException e) {
            LOGGER.error("Error in retrieving Identity Details");
            throw new OAuthIdentityProviderServiceException(e.getMessage());
        }
    }

    public void deleteServiceInstance(String instanceId) throws OAuthIdentityProviderServiceException {
        try {
            LOGGER.info(String.format("Deleting instance with id %s",  instanceId));
            restTemplate.exchange(getUriForCloneDelete(instanceId), DELETE, getEntity(), String.class);
        } catch (HttpStatusCodeException e) {
            LOGGER.error(String.format("Error in deleting instance with id %s. %s",  instanceId, e.getMessage()));
            throw new OAuthIdentityProviderServiceException(e.getMessage());
        }
    }

    private HttpEntity<?> getEntity() {
        return new HttpEntity<>(getHttpHeaders());
    }

    private HttpEntity<?> getCreationEntity(String instanceId) {
        return new HttpEntity<>(getBody(instanceId), getHttpHeaders());
    }


    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, credentialsProvider.getCredentialsAsString());

        return headers;
    }

    private String getBody(String instanceId) {
        return String.format("{\"xsappname\":\"%s\",\"authorities\":[\"$XSMASTERAPPNAME.event.service-producer\",\"$XSMASTERAPPNAME.event.service-consumer\"]}", instanceId);
    }

    private String getUriForCloneDetails(String instanceId) {
        return String.format(XSUAA_IDENTITY_CLONE_URL_TEMPLATE, xsUaaBaseUrl, instanceId);
    }

    private String getUriForCreationCloneDetails(String instanceId) {
        return String.format(XSUAA_IDENTITY_CLONE_URL_TEMPLATE, xsUaaBaseUrl, instanceId);
    }

    private String getUriForBindingDetails(String instanceId) {
        return String.format(XSUAA_IDENTITY_CLONE_BINDING_DETAILS_URL_TEMPLATE, xsUaaBaseUrl, instanceId);
    }

    private String getUriForCloneDelete(String instanceId) {
        return String.format(XSUAA_IDENTITY_CLONE_URL_TEMPLATE, xsUaaBaseUrl, String.format("?orgid=%s&serviceinstanceid=%s",orgGuid,instanceId));
    }

    public boolean isExistingClient(String instanceId) {
        try {
            LOGGER.info("Retrieving Identity Details");
            retrieveBindingDetails(instanceId);
            return true;
        } catch (OAuthIdentityProviderServiceException e) {
            LOGGER.error("Error in retrieving Binding Details");
            return false;
        }
    }

}
