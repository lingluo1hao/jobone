package com.luo.jobtest.jobone.service;

import com.luo.jobtest.jobone.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(String id);
    List<User> findAll();

    User save(User user);

    void delete(String id);
    void batchInsert(List<User> list);
}
