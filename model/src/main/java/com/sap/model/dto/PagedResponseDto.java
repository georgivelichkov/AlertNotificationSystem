package com.sap.model.dto;

import com.sap.model.builders.PagedResponseBuilder;
import com.sap.model.interfaces.IPagedResponse;

import java.util.List;

public class PagedResponseDto<T> implements IPagedResponse<T> {

    private final ResponseMetaDataDto metaData;
    private List<T> results;

    public PagedResponseDto(ResponseMetaDataDto metaData, List<T> results) {
        this.metaData = metaData;
        this.results = results;
    }

    public static PagedResponseBuilder builder() {
        return new PagedResponseBuilder<>();
    }

    @Override
    public ResponseMetaDataDto getMetaData() {
        return metaData;
    }

    @Override
    public List<T> getResults() {
        return results;
    }

}
