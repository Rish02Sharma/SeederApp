package com.zemoso.seeder.service;

import com.zemoso.seeder.dto.UserDto;
import com.zemoso.seeder.entity.User;

public interface UserService {
    User getById(Long id) throws Exception;
    User createUser(UserDto request);
    User updateUser(User oldUser, User newUser);
}
