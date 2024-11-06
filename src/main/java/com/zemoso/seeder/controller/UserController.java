package com.zemoso.seeder.controller;

import com.zemoso.seeder.entity.Users;
import com.zemoso.seeder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/user/{id}")
    Users one(@PathVariable Long id) throws Exception {

        return userRepository.findById(id)
                .orElseThrow(() -> new Exception("Something"));
    }
}
