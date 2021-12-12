package com.sap.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ResponseMetaDataDto implements Serializable {

    private int currentPage;
    private int pageSize;
    private int totalPages;
    private int totalElements;

    @JsonCreator
    public ResponseMetaDataDto(@JsonProperty("currentPage") int currentPage, @JsonProperty("pageSize") int pageSize,
                               @JsonProperty("totalPages") int totalPages, @JsonProperty("totalElements") int totalElements ) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }
}
