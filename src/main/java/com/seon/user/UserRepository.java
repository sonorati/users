package com.seon.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByFirstNameContainingIgnoreCase(String firstName);
    List<User> findByFirstNameContainingIgnoreCaseAndSurnameContainingIgnoreCase(String firstName, String surname);
    List<User> findBySurnameContainingIgnoreCase(String surname);
}
