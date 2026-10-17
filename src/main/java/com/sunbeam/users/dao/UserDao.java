package com.sunbeam.users.dao;

import java.util.List;
import java.util.Optional;

import com.sunbeam.users.domain.User;

public interface UserDao {
	int save(User user);

    int updateById(User user);

    int updateEmail(String email, int id);

    int deleteUser(int id);

    List<User> findAll();

    User findUserById(int id);

    Optional<User> findByEmail(String email);
}
