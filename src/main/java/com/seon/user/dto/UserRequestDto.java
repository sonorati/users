package com.seon.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRequestDto)) return false;
        UserRequestDto that = (UserRequestDto) o;
        return Objects.equals(title, that.title) && Objects.equals(firstName, that.firstName) && Objects.equals(surname, that.surname) && Objects.equals(dob, that.dob) && Objects.equals(jobTitle, that.jobTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, firstName, surname, dob, jobTitle);
    }
}
