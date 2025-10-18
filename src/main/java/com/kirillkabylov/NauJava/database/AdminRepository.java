package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Admin;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminRepository extends MemoryRepository<Admin> {
    public AdminRepository(List<Admin> storage) {
        super(storage);
    }
}
