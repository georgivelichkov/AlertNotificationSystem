package com.sap.servicebroker.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static java.util.Collections.unmodifiableCollection;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

public class IdentityDetails {
    private final String xsappname;
    private final Collection<String> authorities;

    @JsonCreator
    public IdentityDetails( //
                                 @JsonProperty("xsappname") String xsappname, //
                                 @JsonProperty("authorities") Collection<String> authorities //
    ) {
        this.xsappname = xsappname;
        this.authorities = new ArrayList<>(emptyIfNull(authorities));
    }

    @JsonProperty("xsappname")
    public String getXsappname() {
        return xsappname;
    }

    @JsonProperty("authorities")
    public Collection<String> getAuthorities() {
        return unmodifiableCollection(authorities);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        IdentityDetails that = (IdentityDetails) o;
        return Objects.equals(xsappname, that.xsappname) && Objects.equals(authorities, that.authorities);
    }

    @Override
    public int hashCode() {

        return Objects.hash(xsappname, authorities);
    }

}
