package com.sap.model.builders;

import com.sap.model.dto.ResponseMetaDataDto;
import com.sap.model.dto.PagedResponseDto;

import java.util.List;

public class PagedResponseBuilder<T> {
    private ResponseMetaDataDto metaData;
    private List<T> results;

    public PagedResponseBuilder withMetaData(ResponseMetaDataDto metaData) {
        this.metaData = metaData;
        return this;
    }

    public PagedResponseBuilder withResults(List<T> results) {
        this.results = results;
        return this;
    }

    public PagedResponseDto<T> build() {
        return new PagedResponseDto<>(metaData, results);
    }
}
