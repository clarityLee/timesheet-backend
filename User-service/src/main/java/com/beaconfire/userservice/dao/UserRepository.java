package com.beaconfire.userservice.dao;

import com.beaconfire.userservice.domain.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    // ↓↓ ------------ Yun-Jing ------------ ↓↓
    public Optional<User> findByUsernameAndPassword(String username, String password);
    public Optional<User> findByUsername(String username);

    // ↓↓------------- Cynthia --------------------- ↓↓




    // ↓↓ -------------- Xian ------------------------ ↓↓





}
