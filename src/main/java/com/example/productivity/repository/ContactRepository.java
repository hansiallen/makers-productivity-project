package com.example.productivity.repository;

import com.example.productivity.model.Contact;
import org.springframework.data.repository.CrudRepository;

public interface ContactRepository extends CrudRepository<Contact,Long> {
}
