package com.task.sqlapp.repos;

import com.task.sqlapp.models.MainTable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MainTableRepository extends CrudRepository<MainTable, Long> {

    @Query("FROM MainTable WHERE id = ?1")
    Iterable<MainTable> findAllById(Long data);

    @Query("FROM MainTable WHERE name = ?1")
    Iterable<MainTable> findAllByName(String data);

    @Query("FROM MainTable WHERE manufacturer = ?1")
    Iterable<MainTable> findAllByManufacturer(String data);

    @Query("FROM MainTable WHERE price = ?1")
    Iterable<MainTable> findAllByPrice(Integer data);

}
