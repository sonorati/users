package com.seon.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.seon.user.validation.ValidateDOB;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@ToString
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequestDto {
    private String title;
    @NotBlank(message = "Firstname is mandatory")
    private String firstName;
    @NotBlank(message = "Surname is mandatory")
    private String surname;
    @ValidateDOB
    @DateTimeFormat(pattern="dd/MM/yyyy")
    @Schema(description = "Date of birth", example = "12/08/2000")
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
