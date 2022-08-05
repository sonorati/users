package com.seon.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserResponseDto)) return false;
        UserResponseDto that = (UserResponseDto) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(firstName, that.firstName) && Objects.equals(surname, that.surname) && Objects.equals(dob, that.dob) && Objects.equals(jobTitle, that.jobTitle) && Objects.equals(createdDate, that.createdDate) && Objects.equals(updatedDate, that.updatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, firstName, surname, dob, jobTitle, createdDate, updatedDate);
    }
}
