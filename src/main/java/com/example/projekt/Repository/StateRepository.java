package com.example.projekt.Repository;

import com.example.projekt.Entity.State;
import org.springframework.data.repository.CrudRepository;

public interface StateRepository extends CrudRepository<State, String> {
    State findStateById(String id);
}