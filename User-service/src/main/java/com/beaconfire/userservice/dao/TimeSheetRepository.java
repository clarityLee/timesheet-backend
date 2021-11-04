package com.beaconfire.userservice.dao;

import com.beaconfire.userservice.domain.TimeSheet;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TimeSheetRepository extends MongoRepository<TimeSheet, ObjectId> {

    // ↓↓ ------------ Yun-Jing ------------ ↓↓


    // ↓↓ -------------- Xian ------------------------ ↓↓



}
