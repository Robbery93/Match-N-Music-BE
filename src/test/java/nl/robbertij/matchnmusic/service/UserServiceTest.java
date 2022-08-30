package nl.robbertij.matchnmusic.service;

import nl.robbertij.matchnmusic.MatchNMusicApplication;
import nl.robbertij.matchnmusic.dto.request.UserPostRequestDto;
import nl.robbertij.matchnmusic.model.Authority;
import nl.robbertij.matchnmusic.model.User;
import nl.robbertij.matchnmusic.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = {MatchNMusicApplication.class})
@EnableConfigurationProperties
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private User user;
    @Mock
    private Authority authority;
    @Mock
    private Set<Authority> authorities;

    @BeforeEach
    void setup() {

        authority = new Authority("Robbery93", "ROLE_USER");
        authorities = new HashSet<>();
        authorities.add(authority);
        String encryptedPassword = passwordEncoder.encode("Robbery123!");

        user = new User();
        user.setUsername("Robbery93");
        user.setPassword(encryptedPassword);
        user.setEnabled(true);
        user.setAuthorities(authorities);
    }

    @DisplayName("Get User by username")
    @Test
    void getUser() {
        when(userRepository.findById(user.getUsername())).thenReturn(Optional.ofNullable(user));

        User found = userService.getUser("Robbery93");

        assertEquals(user.getUsername(), found.getUsername());
    }

    @DisplayName("Delete user by Id")
    @Test
    void deleteUser() {
        String username = user.getUsername();

        when(userRepository.findById(username)).thenReturn(Optional.ofNullable(user));

        userService.deleteUser(username);

        verify(userRepository, times(1)).deleteById(username);
    }

    @DisplayName("Create a new User with student role")
    @Test
    void createStudent() {
        UserPostRequestDto requestDto = new UserPostRequestDto();
        requestDto.setUsername("Robbert");
        requestDto.setPassword("Wachtwoord123!");

        String username = user.getUsername();

        when(userRepository.findById(username)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        String usernameOfNewUser = userService.createStudent(requestDto);

        User found = userService.getUser(usernameOfNewUser);

        assertThat(usernameOfNewUser).isSameAs(found.getUsername());
    }

    @DisplayName("Create a new User with student role")
    @Test
    void createTeacher() {
        UserPostRequestDto requestDto = new UserPostRequestDto();
        requestDto.setUsername("Robbert");
        requestDto.setPassword("Wachtwoord123!");

        String username = user.getUsername();

        when(userRepository.findById(username)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        String usernameOfNewUser = userService.createTeacher(requestDto);

        User found = userService.getUser(usernameOfNewUser);

        assertThat(usernameOfNewUser).isSameAs(found.getUsername());
    }


    @DisplayName("Get Authorities of User by username")
    @Test
    void getAuthorities() {
        String username = user.getUsername();

        when(userRepository.findById(username)).thenReturn(Optional.ofNullable(user));

        Set<Authority> expected = user.getAuthorities();
        Set<Authority> actual = userService.getAuthorities(username);

        assertEquals(expected, actual);
    }

    @DisplayName("Add Authority to authorities")
    @Test
    void addAuthority() {
        String username = user.getUsername();

        when(userRepository.findById(username)).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.addAuthority(username, "random");

        Set<Authority> foundAuthorities = user.getAuthorities();

        assertEquals(2, foundAuthorities.size());
    }

    @DisplayName("Remove Authority")
    @Test
    void removeAuthority() {
        String username = user.getUsername();

        when(userRepository.findById(username)).thenReturn(Optional.ofNullable(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.removeAuthority(username, "ROLE_USER");

        Set<Authority> foundAuthorities = user.getAuthorities();

        assertEquals(0, foundAuthorities.size());
    }
}