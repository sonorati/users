package com.seon.user.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode
@ToString
@Builder
@Getter
@Setter
public class UserResponseDto {
    private UUID id;
    private String title;
    private String firstName;
    private String surname;
    private String dob;
    private String jobTitle;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
