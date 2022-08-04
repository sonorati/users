package com.seon.user;

import com.seon.user.dto.UserRequestDto;
import com.seon.user.dto.UserResponseDto;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserMapping {

    public static User toEntity(UserRequestDto dto) {
        return toEntity(UUID.randomUUID(), dto);
    }

    public static User toEntity(UUID userId, UserRequestDto dto) {
        LocalDateTime now = LocalDateTime.now();
        return User.builder()
                .id(userId)
                .title(dto.getTitle())
                .firstName(dto.getFirstName())
                .surname(dto.getSurname())
                .dob(dto.getDob())
                .jobTitle(dto.getJobTitle())
                .createdDate(now)
                .updatedDate(now)
                .build();
    }

    public static void mapToEntity(UserRequestDto dto, User user) {
        user.setFirstName(dto.getFirstName());
        user.setSurname(dto.getSurname());
        user.setDob(dto.getDob());
        user.setTitle(dto.getTitle());
        user.setJobTitle(dto.getJobTitle());
        user.setUpdatedDate(LocalDateTime.now());
    }
    public static UserResponseDto toDto(User entity) {
        return UserResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .firstName(entity.getFirstName())
                .surname(entity.getSurname())
                .dob(entity.getDob())
                .jobTitle(entity.getJobTitle())
                .createdDate(entity.getCreatedDate())
                .updatedDate(entity.getUpdatedDate())
                .build();
    }
}
