package com.sap.servicebroker.interfaces;

import com.sap.servicebroker.dto.OAuth2Credentials;
import com.sap.servicebroker.exceptions.OAuthIdentityProviderServiceException;

public interface IOAuthIdentityProviderService {

    OAuth2Credentials get(String instanceId) throws OAuthIdentityProviderServiceException;

    void delete(String instanceId);
}
