package com.sap.model.interfaces;

import com.sap.model.dto.ResponseMetaDataDto;

import java.util.List;

public interface IPagedResponse<T> {

    ResponseMetaDataDto getMetaData();
    List<T> getResults();

}
