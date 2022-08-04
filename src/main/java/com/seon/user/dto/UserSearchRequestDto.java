package com.seon.user.dto;

import lombok.*;

import java.util.UUID;

@EqualsAndHashCode
@ToString
@Builder
@Getter
@Setter
public class UserSearchRequestDto {
    private UUID id;
    private String firstName;
    private String surname;
}
