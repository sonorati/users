package com.seon.user;

import com.seon.user.dto.UserRequestDto;
import com.seon.user.dto.UserResponseDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final UUID USER_ID = UUID.randomUUID();
    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    public void whenSaveUserShouldReturnSavedUser() {
        User user = makeUser();
        UserRequestDto dto = UserRequestDto.builder().build();
        when(repository.save(any(User.class))).thenReturn(user);

        UserResponseDto userDto = service.saveUser(dto);

        assertThat(userDto).isNotNull();
        assertThat(userDto)
                .isEqualTo(UserResponseDto.builder()
                        .id(USER_ID)
                        .title("Mr")
                        .firstName("Joe")
                        .surname("Doe")
                        .jobTitle("Developer")
                        .build());
    }

    @Test
    public void whenValidUserIdIsGiven_shouldFindAndReturned() {
        User user = makeUser();
        when(repository.findById(USER_ID)).thenReturn(Optional.of(user));

        UserResponseDto dto = service.getUser(USER_ID);

        assertThat(dto).isNotNull();
        assertThat(dto).isEqualTo(UserResponseDto.builder()
                .id(USER_ID)
                .title("Mr")
                .firstName("Joe")
                .surname("Doe")
                .jobTitle("Developer")
                .build());
    }

    @Test
    public void whenValidUserIdIsGiven_shouldFindOptionalOfUser() {
        User user = makeUser();
        when(repository.findById(USER_ID)).thenReturn(Optional.of(user));

        Optional<User> savedUser = service.findUser(USER_ID);

        assertThat(savedUser).isNotEmpty();
        assertThat(savedUser.get()).isEqualTo(User.builder()
                .id(USER_ID)
                .title("Mr")
                .firstName("Joe")
                .surname("Doe")
                .jobTitle("Developer")
                .build());
    }

    @Test
    public void whenNotFoundUserIdIsGiven_shouldReturnEmptyOption() {

        when(repository.findById(USER_ID)).thenReturn(Optional.empty());

        Optional<User> notFoundUser = service.findUser(USER_ID);

        assertThat(notFoundUser).isEmpty();
    }

    @Test(expected = NotFoundException.class)
    public void whenNotFoundUserIdIsGiven_shouldFindAndReturned() {
        when(repository.findById(USER_ID)).thenReturn(Optional.empty());

        service.getUser(USER_ID);
    }

    @Test
    public void whenUpdatedTitle_shouldStoreAndRetrieveUpdatedUser() {
        User user = makeUser();
        UserRequestDto dto = UserRequestDto.builder()
                .title("Dr")
                .build();
        when(repository.save(user)).thenReturn(user);

        UserResponseDto updateUser = service.updateUser(dto, user);

        assertThat(updateUser.getTitle()).isEqualTo("Dr");
    }

    @Test
    public void whenNewUserIsPUT_shouldStoreAndRetrieve() {
        User user = makeUser();
        UserRequestDto dto = UserRequestDto.builder()
                .title("Mr")
                .firstName("Joe")
                .surname("Doe")
                .jobTitle("Developer")
                .build();
        when(repository.save(any(User.class))).thenReturn(user);

        UserResponseDto updateUser = service.saveUserWithId(USER_ID, dto);

        assertThat(updateUser).isEqualTo(UserResponseDto.builder()
                .id(USER_ID)
                .title("Mr")
                .firstName("Joe")
                .surname("Doe")
                .jobTitle("Developer")
                .build());
    }

    @Test(expected = NotFoundException.class)
    public void whenDeletingWithInvalidUserId_shouldRemoveUser() {
        doThrow(new RuntimeException()).when(repository).deleteById(USER_ID);
        service.deleteUser(USER_ID);
    }

    private static User makeUser() {
        return User.builder()
                .id(USER_ID)
                .title("Mr")
                .firstName("Joe")
                .surname("Doe")
                .jobTitle("Developer")
                .build();
    }
}