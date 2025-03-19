package com.example.javamassagaapp.dao;

import javafx.collections.ObservableList;

import java.util.List;

public interface GenericDAO<T> {
    /**
     * Find entity by ID
     */

    T findById(int id);

    /**
     * Create a new entity
     *
     * @param fname
     * @param lastname
     * @param date
     * @param time
     * @param status
     * @param price
     */

    void create(String fname, String lastname, String date, String time, int status, int price);

    /**
     * Update entity
     *
     */

    void update(T entity);

    /**
     * Delete an entity
     *
     * @param id
     */

    void delete(int id);

    /**
     * Find all entities by type
     */

    List<T> all();

    ObservableList<T> findAll();

}
