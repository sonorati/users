package com.seon.user;

import com.seon.user.dto.UserRequestDto;
import com.seon.user.dto.UserResponseDto;
import com.seon.user.dto.UserSearchRequestDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.seon.user.UserMapping.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class UserService {

    private final Logger log = LogManager.getLogger(getClass());
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto getUser(UUID userId) {
        return findUser(userId)
                .map(UserMapping::toDto)
                .orElseThrow(() -> handleNotFound(userId));
    }

    public Optional<User> findUser(UUID userId) {
        return userRepository.findById(userId);
    }

    public void deleteUser(UUID userId) {
        try {
            userRepository.deleteById(userId);
        } catch (RuntimeException ex) {
            throw handleNotFound(userId);
        }
    }

    public UserResponseDto saveUser(UserRequestDto dto) {
        User entity = toEntity(dto);
        return store(entity);
    }

    public UserResponseDto saveUserWithId(UUID userId, UserRequestDto dto) {
        User entity = toEntity(userId, dto);
        return store(entity);
    }

    public UserResponseDto updateUser(UserRequestDto dto, User user) {
        mapToEntity(dto, user);
        User savedUser = userRepository.save(user);
        return toDto(savedUser);
    }

    public List<UserResponseDto> searchUsers(UserSearchRequestDto user) {
        if (nonNull(user.getId())) {
            return List.of(getUser(user.getId()));
        }
        if (nonNull(user.getFirstName()) && nonNull(user.getSurname())) {
            return userRepository.findByFirstNameContainingIgnoreCaseAndSurnameContainingIgnoreCase(user.getFirstName(),
                            user.getSurname()).stream()
                    .map(UserMapping::toDto).collect(Collectors.toList());
        }
        if (nonNull(user.getFirstName()) && isNull(user.getSurname())) {
            return userRepository.findByFirstNameContainingIgnoreCase(user.getFirstName()).stream()
                    .map(UserMapping::toDto).collect(Collectors.toList());
        }
        return userRepository.findBySurnameContainingIgnoreCase(user.getSurname()).stream()
                .map(UserMapping::toDto).collect(Collectors.toList());
    }

    private UserResponseDto store(User entity) {
        try {
            User savedUser = userRepository.save(entity);
            return toDto(savedUser);
        } catch (RuntimeException ex) {
            log.error("Error occurred while trying to save user: {}", entity.getId());
            throw ex;
        }
    }

    private NotFoundException handleNotFound(UUID userId) {
        log.error("User with id: {} could not be found", userId);
        return new NotFoundException("User Not Found");
    }
}
