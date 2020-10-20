package com.test.restfullbackend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.test.restfullbackend.model.User;
import com.test.restfullbackend.model.Views;
import com.test.restfullbackend.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    @JsonView(Views.NameAndLoginAndPassw.class)
    public List<User> list() {
        return userRepository.findAll();
    }

    @GetMapping("{login}")
    public User getOneUser(@PathVariable("login") User user) {
        return user;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("{login}")
    public User update(@PathVariable("login") User userFromDb,
                       @RequestBody User user) {
        BeanUtils.copyProperties(user, userFromDb, "login");
        return userRepository.save(user);
    }

    @DeleteMapping("{login}")
    public void delete(@PathVariable("login") User user) {
        userRepository.delete(user);
    }
}
