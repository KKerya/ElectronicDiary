package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AdminRepository extends CrudRepository<Admin, Long> {
}
