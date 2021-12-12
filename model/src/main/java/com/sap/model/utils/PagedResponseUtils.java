package com.sap.model.utils;

import com.sap.model.dto.ResponseMetaDataDto;
import com.sap.model.exceptions.response.PageRequestOutOfBounds;
import com.sap.model.exceptions.response.ResultsRequestOutOfBounds;
import com.sap.model.factories.BuilderFactory;
import com.sap.model.interfaces.IDAOService;

public class PagedResponseUtils {

    private Integer pageRequest;
    private Integer resultsRequest;
    private IDAOService service;

    private PagedResponseUtils(Integer pageRequest, Integer resultsRequest, IDAOService service) {
        this.pageRequest = pageRequest;
        this.resultsRequest = resultsRequest;
        this.service = service;
    }

    public static PagedResponseUtils newInstance(Integer pageRequest, Integer resultsRequest, IDAOService service) {
        return new PagedResponseUtils(pageRequest, resultsRequest, service);
    }

    public PagedResponseUtils validateInput() throws PageRequestOutOfBounds, ResultsRequestOutOfBounds {
        checkResultsRequest(resultsRequest);
        checkPageRequest(pageRequest);
        return this;
    }

    public ResponseMetaDataDto createMetaData() {

        int maxPages = service.pageOf(pageRequest, resultsRequest).getTotalPages();
        int maxElements = (int) service.pageOf(pageRequest, resultsRequest).getTotalElements();

        return BuilderFactory.metaData()
                .withCurrentPage(pageRequest)
                .withPageSize(resultsRequest)
                .withTotalPages(maxPages)
                .withTotalElements(maxElements)
                .build();
    }

    private void checkPageRequest(Integer pageRequest) throws PageRequestOutOfBounds {
        int page = pageRequest == null ? 0 : pageRequest;

        int maxPages = calculateMaxPages();

        if(page > maxPages) {
            throw new PageRequestOutOfBounds("Requested page exceeds max pages!!!");
        }

        this.pageRequest = page;
    }

    private void checkResultsRequest(Integer resultsRequest) throws ResultsRequestOutOfBounds {
        int results = resultsRequest == null ? 1 : resultsRequest;

        if( results > service.listAll().size() ) {
            throw new ResultsRequestOutOfBounds("Requested results not available!!! Available results: " + service.count() );
        }

        if( results > 100 ) {
            throw new ResultsRequestOutOfBounds("Requested results exceed max results allowed!!! Max allowed are 100");
        }

        if( results < 1) {
            throw new ResultsRequestOutOfBounds("Requested results must be greater than 0!!!");
        }

        this.resultsRequest = results;
    }

    private int calculateMaxPages() {
        return (int) service.count() / resultsRequest;
    }

    public Integer getPageRequest() {
        return pageRequest;
    }

    public Integer getResultsRequest() {
        return resultsRequest;
    }
}
