package com.example.productivity.repository;

import com.example.productivity.model.RollingCode;
import org.springframework.data.repository.CrudRepository;

public interface RollingCodeRepository extends CrudRepository<RollingCode, Long> {
    boolean existsByCode(Long code);
}
