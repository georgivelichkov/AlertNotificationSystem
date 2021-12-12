package com.sap.credentialsprovider;

public interface ICredentialsProvider {

    long getExpirationTime();

    String getCredentialsAsString();
}
