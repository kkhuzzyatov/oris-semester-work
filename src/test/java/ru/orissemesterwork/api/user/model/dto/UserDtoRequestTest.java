package ru.orissemesterwork.api.user.model.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoRequestTest {
    private static final String NAME = "Камиль";
    private static final String EMAIL = "test@gmail.com";
    private static final String PHONE = "89874146494";
    private static final String PASSWORD = "StrongPass123";
    private static final Integer CITY_ID = 1;
    private static final Integer POINT_ID = 1;

    @Test
    @DisplayName("Проверка установки и получения имени")
    void testName() {
        UserDtoRequest dto = new UserDtoRequest();
        dto.setName(NAME);
        assertEquals(NAME, dto.getName());
    }

    @Test
    @DisplayName("Проверка установки и получения email")
    void testEmail() {
        UserDtoRequest dto = new UserDtoRequest();
        dto.setEmail(EMAIL);
        assertEquals(EMAIL, dto.getEmail());
    }

    @Test
    @DisplayName("Проверка установки и получения телефона")
    void testPhone() {
        UserDtoRequest dto = new UserDtoRequest();
        dto.setPhone(PHONE);
        assertEquals(PHONE, dto.getPhone());
    }

    @Test
    @DisplayName("Проверка установки и получения пароля")
    void testPassword() {
        UserDtoRequest dto = new UserDtoRequest();
        dto.setPassword(PASSWORD);
        assertEquals(PASSWORD, dto.getPassword());
    }

    @Test
    @DisplayName("Проверка установки и получения cityId")
    void testCityId() {
        UserDtoRequest dto = new UserDtoRequest();
        dto.setCityId(CITY_ID);
        assertEquals(CITY_ID, dto.getCityId());
    }

    @Test
    @DisplayName("Проверка установки и получения pointId")
    void testPointId() {
        UserDtoRequest dto = new UserDtoRequest();
        dto.setPointId(POINT_ID);
        assertEquals(POINT_ID, dto.getPointId());
    }
}
