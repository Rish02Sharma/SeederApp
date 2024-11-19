package com.zemoso.seeder.service.impl;

import com.zemoso.seeder.dto.UserDto;
import com.zemoso.seeder.entity.User;
import com.zemoso.seeder.exception.ResourceNotFoundException;
import com.zemoso.seeder.repository.UserRepository;
import com.zemoso.seeder.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    @Override
    public User createUser(UserDto request) {
        User user = modelMapper.map(request, User.class);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User newUser) {
        User oldUser = getById(newUser.getId());
        oldUser.setName(newUser.getName()!=null?newUser.getName(): oldUser.getName());
        oldUser.setEmail(newUser.getEmail()!=null?newUser.getEmail(): oldUser.getEmail());
        oldUser.setPassword(newUser.getPassword()!=null?newUser.getPassword(): oldUser.getPassword());
        oldUser.setAvailableCredit(newUser.getAvailableCredit()!=0d?newUser.getAvailableCredit(): oldUser.getAvailableCredit());
        return userRepository.save(oldUser);
    }
}
