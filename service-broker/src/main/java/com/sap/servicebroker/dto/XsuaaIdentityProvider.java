package com.sap.servicebroker.dto;

import com.sap.credentialsprovider.oauth.OAuth2CredentialsProvider;
import com.sap.servicebroker.enums.OAuthScope;
import com.sap.servicebroker.exceptions.OAuthIdentityProviderServiceException;
import com.sap.servicebroker.interfaces.IOAuthIdentityProviderService;
import com.sap.servicebroker.utils.HTTPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

import static com.sap.servicebroker.enums.OAuthScope.fromXsUaaScope;
import static java.util.stream.Collectors.toList;

public class XsuaaIdentityProvider implements IOAuthIdentityProviderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(XsuaaIdentityProvider.class);
    private final String xsappname;
    private HTTPUtils utils;

    public XsuaaIdentityProvider(RestTemplate restTemplate, OAuth2CredentialsProvider credentialsProvider, String xsUaaBaseUrl, String xsappname) {
        this.utils = new HTTPUtils(restTemplate, credentialsProvider, xsUaaBaseUrl);
        this.xsappname = xsappname;
    }

    @Override
    public OAuth2Credentials get(String instanceId) throws OAuthIdentityProviderServiceException {
        IdentityDetails identityDetails = null;

        if( utils.isExistingClient(instanceId)) {
            identityDetails = utils.retrieveIdentityDetails(instanceId);
        } else {
            identityDetails = utils.createIdentityDetails(instanceId);
        }

        BindingDetails bindingDetails = utils.retrieveBindingDetails(instanceId);

        return OAuth2Credentials.builder()
                                .withClientId(bindingDetails.getClientId())
                                .withClientSecret(bindingDetails.getSecret())
                                .withOAuthServiceURL(bindingDetails.getOAuthServiceUrl())
                                .withOAuthScopes(fromXsUaaScopes(xsappname, identityDetails.getAuthorities()))
                                .build();
    }

    @Override
    public void delete(String instanceId) {
        try {
            utils.deleteServiceInstance(instanceId);
        } catch (OAuthIdentityProviderServiceException e) {
            e.printStackTrace();
        }
    }

    private static Collection<OAuthScope> fromXsUaaScopes(String xsappname, Collection<String> scopes) {
        return scopes.stream() //
                .map(scope -> fromXsUaaScope(xsappname, scope)) //
                .collect(toList());
    }
}
