package ru.orissemesterwork.api.user.model.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.orissemesterwork.api.user.model.entity.User;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private static final Integer USER_ID = 1;
    private static final String USER_SURNAME = "Хуззятов";
    private static final String USER_NAME = "Камиль";
    private static final String USER_PATRONYMIC = "Артурович";
    private static final String USER_EMAIL = "test@gmail.com";
    private static final String USER_PHONE = "89874146494";
    private static final String USER_PASSWORD = "StrongPass123";

    // Builder & Getters
    @Test
    @DisplayName("Проверка установки id")
    void testBuilder_Id() {
        User user = new User();
        user.setId(USER_ID);
        assertEquals(USER_ID, user.getId());
    }

    @Test
    @DisplayName("Проверка установки фамилии")
    void testBuilder_Surname() {
        User user = new User();
        user.setSurname(USER_SURNAME);
        assertEquals(USER_SURNAME, user.getSurname());
    }

    @Test
    @DisplayName("Проверка установки имени")
    void testBuilder_Name() {
        User user = new User();
        user.setName(USER_NAME);
        assertEquals(USER_NAME, user.getName());
    }

    @Test
    @DisplayName("Проверка установки отчества")
    void testBuilder_Patronymic() {
        User user = new User();
        user.setPatronymic(USER_PATRONYMIC);
        assertEquals(USER_PATRONYMIC, user.getPatronymic());
    }

    @Test
    @DisplayName("Проверка установки email")
    void testBuilder_Email() {
        User user = new User();
        user.setEmail(USER_EMAIL);
        assertEquals(USER_EMAIL, user.getEmail());
    }

    @Test
    @DisplayName("Проверка установки телефона")
    void testBuilder_Phone() {
        User user = new User();
        user.setPhone(USER_PHONE);
        assertEquals(USER_PHONE, user.getPhone());
    }

    // Email validation
    @Test
    @DisplayName("Невозможно установить пустое имя")
    void testInvalidName_Empty() {
        User user = new User();
        assertThrows(IllegalArgumentException.class, () -> user.setName(""));
    }

    @Test
    @DisplayName("Невозможно установить имя из пробелов")
    void testInvalidName_Blank() {
        User user = new User();
        assertThrows(IllegalArgumentException.class, () -> user.setName("   "));
    }

    @Test
    @DisplayName("Имя устанавливается корректно, если не пустое")
    void testValidName() {
        User user = new User();
        user.setName(USER_NAME);
        assertEquals(USER_NAME, user.getName());
    }

    // Email validation
    @Test
    @DisplayName("Валидный email сохраняется корректно")
    void testValidEmail() {
        User user = new User();
        user.setEmail(USER_EMAIL);
        assertEquals(USER_EMAIL, user.getEmail());
    }

    @Test
    @DisplayName("Невалидный email без @ выбрасывает исключение")
    void testInvalidEmail_NoAt() {
        User user = new User();
        assertThrows(IllegalArgumentException.class, () -> user.setEmail("usergmail.com"));
    }

    @Test
    @DisplayName("Невалидный email с неподдерживаемым доменом выбрасывает исключение")
    void testInvalidEmail_UnsupportedDomain() {
        User user = new User();
        assertThrows(IllegalArgumentException.class, () -> user.setEmail("user@yahoo.com"));
    }

    @Test
    @DisplayName("Невалидный email без доменной зоны выбрасывает исключение")
    void testInvalidEmail_NoTld() {
        User user = new User();
        assertThrows(IllegalArgumentException.class, () -> user.setEmail("user@gmail"));
    }

    // Phone validation
    @Test
    @DisplayName("Валидный телефон сохраняется корректно")
    void testValidPhone() {
        User user = new User();
        user.setPhone(USER_PHONE);
        assertEquals(USER_PHONE, user.getPhone());
    }

    @Test
    @DisplayName("Невалидный телефон (короткий) выбрасывает исключение")
    void testInvalidPhone_Short() {
        User user = new User();
        assertThrows(IllegalArgumentException.class, () -> user.setPhone("8999123"));
    }

    @Test
    @DisplayName("Невалидный телефон (начинается не с 8) выбрасывает исключение")
    void testInvalidPhone_NotStartsWith8() {
        User user = new User();
        assertThrows(IllegalArgumentException.class, () -> user.setPhone("79991234567"));
    }

    // Password validation
    @Test
    @DisplayName("Валидный пароль хэшируется")
    void testValidPasswordHashing() {
        User user = new User();
        user.setPassword(USER_PASSWORD);
        assertNotNull(user.getPasswordHash());
    }

    @Test
    @DisplayName("Слишком короткий пароль выбрасывает исключение")
    void testInvalidPassword_Short() {
        User user = new User();
        assertThrows(IllegalArgumentException.class, () -> user.setPassword("123"));
    }

    @Test
    @DisplayName("Метод checkPassword возвращает true для правильного пароля")
    void testCheckPassword_Valid() {
        User user = new User();
        user.setPassword(USER_PASSWORD);
        assertTrue(user.checkPassword(USER_PASSWORD));
    }

    @Test
    @DisplayName("Метод checkPassword возвращает false для неверного пароля")
    void testCheckPassword_Invalid() {
        User user = new User();
        user.setPassword(USER_PASSWORD);
        assertFalse(user.checkPassword("WrongPass123"));
    }
}
