package ru.orissemesterwork.api.user.model.entity;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private static final Integer USER_ID = 1;
    private static final String USER_NAME = "Камиль";
    private static final String USER_EMAIL = "test@gmail.com";
    private static final String USER_PHONE = "89874146494";
    private static final String USER_PASSWORD = "StrongPass123";
    private static final Integer USER_CITY_ID = 1;
    private static final Integer USER_POINT_ID = 1;

    @Test
    @DisplayName("Проверка установки id")
    void testSetId() {
        User user = new User();
        user.setId(USER_ID);
        assertEquals(USER_ID, user.getId());
    }

    @Test
    @DisplayName("Проверка установки имени")
    void testSetName() {
        User user = new User();
        user.setName(USER_NAME);
        assertEquals(USER_NAME, user.getName());
    }

    @Test
    @DisplayName("Невозможно установить пустое имя")
    void testSetNameEmpty() {
        User user = new User();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> user.setName(""));
        assertTrue(exception.getMessage().contains(User.ERROR_EMPTY_NAME));
    }

    @Test
    @DisplayName("Невозможно установить имя из пробелов")
    void testSetNameBlank() {
        User user = new User();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> user.setName("   "));
        assertTrue(exception.getMessage().contains(User.ERROR_EMPTY_NAME));
    }

    @Test
    @DisplayName("Проверка установки email")
    void testSetEmail() {
        User user = new User();
        user.setEmail(USER_EMAIL);
        assertEquals(USER_EMAIL, user.getEmail());
    }

    @Test
    @DisplayName("Невалидный email без @ выбрасывает исключение")
    void testInvalidEmailNoAt() {
        User user = new User();
        assertThrows(IllegalArgumentException.class, () -> user.setEmail("usergmail.com"));
    }

    @Test
    @DisplayName("Невалидный email с неподдерживаемым доменом выбрасывает исключение")
    void testInvalidEmailUnsupportedDomain() {
        User user = new User();
        assertThrows(IllegalArgumentException.class, () -> user.setEmail("user@yahoo.com"));
    }

    @Test
    @DisplayName("Невалидный email без доменной зоны выбрасывает исключение")
    void testInvalidEmailNoTld() {
        User user = new User();
        assertThrows(IllegalArgumentException.class, () -> user.setEmail("user@gmail"));
    }

    @Test
    @DisplayName("Проверка установки телефона")
    void testSetPhone() {
        User user = new User();
        user.setPhone(USER_PHONE);
        assertEquals(USER_PHONE, user.getPhone());
    }

    @Test
    @DisplayName("Невалидный телефон (короткий) выбрасывает исключение")
    void testInvalidPhoneShort() {
        User user = new User();
        assertThrows(IllegalArgumentException.class, () -> user.setPhone("8999123"));
    }

    @Test
    @DisplayName("Невалидный телефон (не начинается с 8) выбрасывает исключение")
    void testInvalidPhoneNotStartsWith8() {
        User user = new User();
        assertThrows(IllegalArgumentException.class, () -> user.setPhone("79991234567"));
    }

    @Test
    @DisplayName("Валидный пароль хэшируется")
    void testPasswordHashing() {
        User user = new User();
        user.setPassword(USER_PASSWORD);
        assertNotNull(user.getPasswordHash());
    }

    @Test
    @DisplayName("Слишком короткий пароль выбрасывает исключение")
    void testInvalidPasswordShort() {
        User user = new User();
        assertThrows(IllegalArgumentException.class, () -> user.setPassword("123"));
    }

    @Test
    @DisplayName("checkPassword возвращает true для правильного пароля")
    void testCheckPasswordValid() {
        User user = new User();
        user.setPassword(USER_PASSWORD);
        assertTrue(user.checkPassword(USER_PASSWORD));
    }

    @Test
    @DisplayName("checkPassword возвращает false для неправильного пароля")
    void testCheckPasswordInvalid() {
        User user = new User();
        user.setPassword(USER_PASSWORD);
        assertFalse(user.checkPassword("WrongPass123"));
    }

    @Test
    @DisplayName("Проверка установки cityId")
    void testSetCityId() {
        User user = new User();
        user.setCityId(USER_CITY_ID);
        assertEquals(USER_CITY_ID, user.getCityId());
    }

    @Test
    @DisplayName("Проверка установки pointId")
    void testSetPointId() {
        User user = new User();
        user.setPointId(USER_POINT_ID);
        assertEquals(USER_POINT_ID, user.getPointId());
    }
}
