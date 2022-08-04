package com.seon.user;

import com.seon.user.dto.UserRequestDto;
import com.seon.user.dto.UserResponseDto;
import com.seon.user.dto.UserSearchRequestDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController()
public class UserController {
    private final Logger log = LogManager.getLogger(getClass());
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/users")
    @ResponseStatus(CREATED)
    public UserResponseDto createUser(@Valid @RequestBody UserRequestDto user) {
        return userService.saveUser(user);
    }

    @PutMapping(value = "/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable UUID userId, @Valid @RequestBody UserRequestDto user) {
        return userService.findUser(userId)
                .map(existingUser -> ResponseEntity.ok(userService.updateUser(user, existingUser)))
                .orElseGet(() -> new ResponseEntity<>(userService.saveUserWithId(userId, user), CREATED));

    }

    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto getUser(@PathVariable UUID userId) {
        return userService.getUser(userId);
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID userId) {
        log.info("Calling delete API for user {}", userId);
        userService.deleteUser(userId);
    }

    @PostMapping("/users/search")
    public Object searchUser(@RequestBody UserSearchRequestDto user) {
        log.info("calling search with id: {} firstName: {} lastName {}", user.getId(), user.getFirstName(), user.getSurname());
        List<UserResponseDto> userResponseDtos = userService.searchUsers(user);
        return ResponseEntity.ok(userResponseDtos);
    }
}
