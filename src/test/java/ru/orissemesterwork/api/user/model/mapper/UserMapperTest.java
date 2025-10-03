package ru.orissemesterwork.api.user.model.mapper;

import org.junit.jupiter.api.*;
import ru.orissemesterwork.api.user.model.dto.UserDtoRequest;
import ru.orissemesterwork.api.user.model.dto.UserDtoResponse;
import ru.orissemesterwork.api.user.model.entity.User;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    private static final Integer USER_ID = 1;
    private static final String USER_NAME = "Камиль";
    private static final String USER_EMAIL = "test@gmail.com";
    private static final String USER_PHONE = "89991112233";
    private static final String USER_PASSWORD = "StrongPass123";
    private static final Integer USER_CITY_ID = 5;
    private static final Integer USER_POINT_ID = 10;

    @Test
    @DisplayName("toEntity корректно преобразует UserDtoRequest → User")
    void testToEntity() {
        UserDtoRequest dto = new UserDtoRequest();
        dto.setName(USER_NAME);
        dto.setEmail(USER_EMAIL);
        dto.setPhone(USER_PHONE);
        dto.setPassword(USER_PASSWORD);
        dto.setCityId(USER_CITY_ID);
        dto.setPointId(USER_POINT_ID);

        User user = UserMapper.toEntity(dto);

        assertTrue(
                user != null
                        && USER_NAME.equals(user.getName())
                        && USER_EMAIL.equals(user.getEmail())
                        && USER_PHONE.equals(user.getPhone())
                        && user.checkPassword(USER_PASSWORD)
                        && USER_CITY_ID.equals(user.getCityId())
                        && USER_POINT_ID.equals(user.getPointId())
        );
    }

    @Test
    @DisplayName("toEntity возвращает null при входном null")
    void testToEntityNull() {
        assertNull(UserMapper.toEntity(null));
    }

    @Test
    @DisplayName("toResponse корректно преобразует User → UserDtoResponse")
    void testToResponse() {
        User user = new User();
        user.setId(USER_ID);
        user.setName(USER_NAME);
        user.setEmail(USER_EMAIL);
        user.setPhone(USER_PHONE);
        user.setPasswordHash(USER_PASSWORD);
        user.setCityId(USER_CITY_ID);
        user.setPointId(USER_POINT_ID);

        UserDtoResponse response = UserMapper.toResponse(user);

        assertTrue(
                response != null
                        && USER_ID.equals(response.getId())
                        && USER_NAME.equals(response.getName())
                        && USER_EMAIL.equals(response.getEmail())
                        && USER_PHONE.equals(response.getPhone())
                        && USER_CITY_ID.equals(response.getCityId())
                        && USER_POINT_ID.equals(response.getPointId())
        );
    }

    @Test
    @DisplayName("toResponse возвращает null при входном null")
    void testToResponseNull() {
        assertNull(UserMapper.toResponse(null));
    }
}

