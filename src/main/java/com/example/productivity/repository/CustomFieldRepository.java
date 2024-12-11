package com.example.productivity.repository;

import com.example.productivity.model.CustomField;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomFieldRepository extends CrudRepository<CustomField,Long> {
    List<CustomField> findByUserId(Long id);
}
