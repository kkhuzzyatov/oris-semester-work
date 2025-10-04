package ru.orissemesterwork.api.user.model.service.impl;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import ru.orissemesterwork.api.user.model.dto.UserDtoRequest;
import ru.orissemesterwork.api.user.model.dto.UserDtoResponse;
import ru.orissemesterwork.api.user.model.entity.User;
import ru.orissemesterwork.api.user.model.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    private static final String USER_NAME = "Камиль";
    private static final String USER_EMAIL = "test@gmail.com";
    private static final String USER_PHONE = "89999999999";
    private static final String USER_PASSWORD = "StrongPass123";
    private static final Integer USER_CITY_ID = 1;
    private static final Integer USER_POINT_ID = 2;

    private UserRepository userRepository;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    private UserDtoRequest buildRequest() {
        UserDtoRequest dto = new UserDtoRequest();
        dto.setName(USER_NAME);
        dto.setEmail(USER_EMAIL);
        dto.setPhone(USER_PHONE);
        dto.setPassword(USER_PASSWORD);
        dto.setCityId(USER_CITY_ID);
        dto.setPointId(USER_POINT_ID);
        return dto;
    }

    @Test
    @DisplayName("Регистрация корректно создает UserDtoResponse")
    void testRegister() {
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(null);

        UserDtoResponse response = userService.register(buildRequest());

        assertTrue(
                response != null
                        && USER_NAME.equals(response.getName())
                        && USER_EMAIL.equals(response.getEmail())
        );
    }

    @Test
    @DisplayName("Регистрация вызывает сохранение пользователя")
    void testRegisterCallsSave() {
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(null);

        userService.register(buildRequest());

        verify(userRepository, times(1)).save(Mockito.any(User.class));
    }

    @Test
    @DisplayName("Регистрация с существующим email выбрасывает исключение")
    void testRegisterEmailAlreadyExists() {
        User existingUser = new User();
        existingUser.setEmail(USER_EMAIL);
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(existingUser);

        assertThrows(IllegalArgumentException.class, () -> userService.register(buildRequest()));
    }

    @Test
    @DisplayName("Регистрация с существующим телефоном выбрасывает исключение")
    void testRegisterPhoneAlreadyExists() {
        User existingUser = new User();
        existingUser.setPhone(USER_PHONE);
        when(userRepository.findByPhone(USER_PHONE)).thenReturn(existingUser);

        assertThrows(IllegalArgumentException.class, () -> userService.register(buildRequest()));
    }

    @Test
    @DisplayName("Успешный логин возвращает корректного пользователя")
    void testLogin() {
        User user = new User();
        user.setName(USER_NAME);
        user.setEmail(USER_EMAIL);
        user.setPassword(USER_PASSWORD);
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(user);

        UserDtoResponse response = userService.login(USER_EMAIL, USER_PASSWORD);

        assertTrue(
                response != null
                        && USER_NAME.equals(response.getName())
                        && USER_EMAIL.equals(response.getEmail())
        );
    }

    @Test
    @DisplayName("Логин с несуществующим email выбрасывает исключение")
    void testLoginUserNotFound() {
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> userService.login(USER_EMAIL, USER_PASSWORD));
    }

    @Test
    @DisplayName("Логин с неверным паролем выбрасывает исключение")
    void testLoginInvalidPassword() {
        User user = new User();
        user.setEmail(USER_EMAIL);
        user.setPassword("CorrectPass123");
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(user);

        assertThrows(IllegalArgumentException.class, () -> userService.login(USER_EMAIL, USER_PASSWORD));
    }
}
