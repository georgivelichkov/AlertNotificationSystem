package com.sap.model.builders;

import com.sap.model.dto.ResponseMetaDataDto;

public class ResponseMetaDataBuilder {

    private int pageSize;
    private int currentPage;
    private int totalPages;
    private int totalElements;

    public ResponseMetaDataBuilder withCurrentPage(int current_page) {
        this.currentPage = current_page;
        return this;
    }

    public ResponseMetaDataBuilder withPageSize(int page_size) {
        this.pageSize = page_size;
        return this;
    }

    public ResponseMetaDataBuilder withTotalPages(int total_pages) {
        this.totalPages = total_pages;
        return this;
    }

    public ResponseMetaDataBuilder withTotalElements(int total_elements) {
        this.totalElements = total_elements;
        return this;
    }


    public ResponseMetaDataDto build() {
        return new ResponseMetaDataDto(currentPage, pageSize, totalPages, totalElements);
    }

}
