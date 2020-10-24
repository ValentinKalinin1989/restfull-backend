package com.test.restfullbackend.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.test.restfullbackend.model.ResultInfo;
import com.test.restfullbackend.model.User;
import com.test.restfullbackend.model.Views;
import com.test.restfullbackend.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Implementing REST API operations: GET, POST, PUT, DELETE
 */
@RestController
@RequestMapping("users")
public class UserController {

    /**
     * repository for CRUD-operations for User
     */
    private final UserRepository userRepository;

    /**
     * constructor
     *
     * @param userRepository - repository for User
     */
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * controller get all users from repository
     *
     * @return - all user as JSON
     */
    @GetMapping
    @JsonView(Views.NameAndLoginAndPassw.class)
    public List<User> list() {
        return userRepository.findAll();
    }

    /**
     * controller get user for login
     *
     * @param user - user with given login
     * @return - user as JSON
     */
    @GetMapping("{login}")
    public ResponseEntity<User> getOneUser(@PathVariable("login") User user) {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * controller create user in repository
     *
     * @param user - created user for save
     * @return - result of create user
     */
    @PostMapping
    public ResponseEntity<ResultInfo> create(@RequestBody User user) {
        ResultInfo resultInfo = ResultInfo.resultTestUser(user);
        if (resultInfo.isSuccess()) {
            userRepository.save(user);
            return new ResponseEntity<>(resultInfo, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(resultInfo, HttpStatus.BAD_REQUEST);
    }

    /**
     * controller update user in repository
     *
     * @param userFromDb - user for repository for update
     * @param user       - user with new param for update userFromDb
     * @return - result of update user
     */
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

    /**
     * controller delete user from repository
     *
     * @param user - user for deleting
     */
    @DeleteMapping("{login}")
    public void delete(@PathVariable("login") User user) {
        userRepository.delete(user);
    }
}
