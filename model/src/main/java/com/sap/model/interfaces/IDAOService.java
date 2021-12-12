package com.sap.model.interfaces;

import org.springframework.data.domain.Page;

import java.util.List;

public interface IDAOService<T> {
    T get(String id) throws Throwable;

    T getByName(String name) throws Throwable;

    Page<T> pageOf(int page, int results);

    List<T> listAll();

    T save(T t);

    void delete(T t) ;

    void findAndDelete(String id) throws Throwable;

    long count();
}
