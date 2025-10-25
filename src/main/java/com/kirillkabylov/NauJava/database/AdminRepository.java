package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin, Long> {
}
