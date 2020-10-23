package com.test.restfullbackend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.test.restfullbackend.model.ResultInfo;
import com.test.restfullbackend.model.User;
import com.test.restfullbackend.model.Views;
import com.test.restfullbackend.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
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
    public ResponseEntity<User> getOneUser(@PathVariable("login") User user) {
        if(user == null) {
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<User> (user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResultInfo> create(@RequestBody User user) {
        ResultInfo resultInfo = ResultInfo.resultTestUser(user);
        if (resultInfo.isSuccess()) {
            userRepository.save(user);
            return new ResponseEntity<>(resultInfo, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(resultInfo, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("{login}")
    public ResponseEntity<ResultInfo> update(@PathVariable("login") User userFromDb,
                             @RequestBody User user) {
        ResultInfo resultInfo = ResultInfo.resultTestUser(user);
        if (resultInfo.isSuccess()) {
            BeanUtils.copyProperties(user, userFromDb, "login");
            userRepository.save(user);
            return new ResponseEntity<>(resultInfo, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(resultInfo, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("{login}")
    public void delete(@PathVariable("login") User user) {
        userRepository.delete(user);
    }
}
