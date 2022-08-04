package com.seon.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode
@ToString
@Builder
@Getter
@Setter
public class UserRequestDto {
    private String title;
    private String firstName;
    @NotBlank(message = "Surname is mandatory")
    private String surname;
    private String dob;
    private String jobTitle;
}
