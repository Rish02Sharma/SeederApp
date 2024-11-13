package com.zemoso.seeder.service;

import com.zemoso.seeder.dto.UserDto;
import com.zemoso.seeder.entity.User;

public interface UserService {
    User getById(Long id);
    User createUser(UserDto request);
    User updateUser(User newUser);
}
