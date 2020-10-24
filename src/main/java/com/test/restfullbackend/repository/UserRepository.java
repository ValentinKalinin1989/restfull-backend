package com.test.restfullbackend.repository;

import com.test.restfullbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * implements simple CRUD operation with Spring Data
 */
public interface UserRepository extends JpaRepository<User, String> {
}
