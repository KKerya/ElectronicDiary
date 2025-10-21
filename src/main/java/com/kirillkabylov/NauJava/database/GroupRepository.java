package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Group, Long> {
}
