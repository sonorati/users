package com.seon.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;
import java.util.UUID;

@ToString
@Builder
@Getter
@Setter
public class UserSearchRequestDto {
    private UUID id;
    private String firstName;
    private String surname;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSearchRequestDto)) return false;
        UserSearchRequestDto that = (UserSearchRequestDto) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(surname, that.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, surname);
    }
}
