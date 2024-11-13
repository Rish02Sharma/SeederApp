package com.zemoso.seeder.controller;

import com.zemoso.seeder.dto.UserDto;
import com.zemoso.seeder.entity.User;
import com.zemoso.seeder.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody UserDto userRequest){
        User user = userService.createUser(userRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User updateRequest) {
        User userResponse = userService.updateUser(updateRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }


}
