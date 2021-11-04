package com.beaconfire.userservice.service;

import com.beaconfire.userservice.dao.UserRepository;
import com.beaconfire.userservice.domain.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ↓↓ ------------ Yun-Jing ------------ ↓↓
    public User login(String username, String password) {

        return userRepository.findByUsernameAndPassword(username, password).orElseGet(User::new);
    }

    public User createRandomUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);
        return user;
    }

    public User queryUserById(String id) {
        ObjectId objectId = new ObjectId(id);
        Optional<User> user = userRepository.findById(objectId);
        return user.orElseGet(User::new);
    }

    public User queryUserByName(String username) {
        return userRepository.findByUsername(username).orElseGet(User::new);
    }

    // ↓↓------------- Cynthia --------------------- ↓↓





    // ↓↓ -------------- Xian ------------------------ ↓↓





}
