package com.beaconfire.timesheetservice.dao;

import com.beaconfire.timesheetservice.domain.TimeSheet;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface TimeSheetRepository extends MongoRepository<TimeSheet, ObjectId> {

    // ↓↓ ------------ Cynthia ------------ ↓↓
    public TimeSheet save(TimeSheet timeSheet);

    public TimeSheet findByUsernameAndWeekEnding(String username, LocalDate weekEnding);

    public List<TimeSheet> findByUsername(String username);

    public List<TimeSheet> findByUsername(String username, Sort sort);
}
