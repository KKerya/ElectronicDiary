package com.kirillkabylov.NauJava.database;

import com.kirillkabylov.NauJava.domain.Report;
import org.springframework.data.repository.CrudRepository;

public interface ReportRepository extends CrudRepository<Report, Long> {
}
