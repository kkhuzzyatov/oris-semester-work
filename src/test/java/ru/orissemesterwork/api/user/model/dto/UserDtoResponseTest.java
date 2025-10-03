package ru.orissemesterwork.api.user.model.dto;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoResponseTest {
    private static final Integer ID = 1;
    private static final String NAME = "Камиль";
    private static final String EMAIL = "test@gmail.com";
    private static final String PHONE = "89874146494";
    private static final Integer CITY_ID = 1;
    private static final Integer POINT_ID = 1;

    @Test
    @DisplayName("Проверка установки и получения id")
    void testId() {
        UserDtoResponse dto = new UserDtoResponse();
        dto.setId(ID);
        assertEquals(ID, dto.getId());
    }

    @Test
    @DisplayName("Проверка установки и получения имени")
    void testName() {
        UserDtoResponse dto = new UserDtoResponse();
        dto.setName(NAME);
        assertEquals(NAME, dto.getName());
    }

    @Test
    @DisplayName("Проверка установки и получения email")
    void testEmail() {
        UserDtoResponse dto = new UserDtoResponse();
        dto.setEmail(EMAIL);
        assertEquals(EMAIL, dto.getEmail());
    }

    @Test
    @DisplayName("Проверка установки и получения телефона")
    void testPhone() {
        UserDtoResponse dto = new UserDtoResponse();
        dto.setPhone(PHONE);
        assertEquals(PHONE, dto.getPhone());
    }

    @Test
    @DisplayName("Проверка установки и получения cityId")
    void testCityId() {
        UserDtoResponse dto = new UserDtoResponse();
        dto.setCityId(CITY_ID);
        assertEquals(CITY_ID, dto.getCityId());
    }

    @Test
    @DisplayName("Проверка установки и получения pointId")
    void testPointId() {
        UserDtoResponse dto = new UserDtoResponse();
        dto.setPointId(POINT_ID);
        assertEquals(POINT_ID, dto.getPointId());
    }
}
