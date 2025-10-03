package ru.orissemesterwork.api.user.model.entity;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String passwordHash;
    private Integer cityId;
    private Integer pointId;

    public static final String ERROR_EMPTY_NAME = "Имя не может быть пустым. Оно должно содержать хотя бы один видимый символ.";
    public static final String ERROR_INVALID_EMAIL = """
    Некорректный email: %s. Допустимые домены: gmail.com, yandex.ru, mail.ru, list.com. 
    Email должен состоять только из строчных латинских букв и цифр перед знаком @.
    """;
    public static final String ERROR_INVALID_PHONE = """
    Некорректный телефон: %s. Телефон должен содержать 11 цифр, начинаться с 8 или +7 и не содержать пробелов или других символов.
    """;
    public static final String ERROR_INVALID_PASSWORD = "Некорректный пароль: %s. Пароль должен быть не короче 8 символов и может содержать любые символы.";

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(ERROR_EMPTY_NAME);
        }
        this.name = name.trim();
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("^[a-z0-9]+@(gmail|yandex|mail|list)\\.(com|ru)$")) {
            throw new IllegalArgumentException(String.format(ERROR_INVALID_EMAIL, email));
        }
        this.email = email.toLowerCase();
    }

    public void setPhone(String phone) {
        if (phone == null) {
            throw new IllegalArgumentException(String.format(ERROR_INVALID_PHONE, phone));
        }
        phone = phone.replaceAll("\\s+", "");
        if (phone.startsWith("+7")) {
            phone = "8" + phone.substring(2);
        }
        if (!phone.matches("^8\\d{10}$")) {
            throw new IllegalArgumentException(String.format(ERROR_INVALID_PHONE, phone));
        }
        this.phone = phone;
    }

    public void setPassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException(String.format(ERROR_INVALID_PASSWORD, password));
        }
        this.passwordHash = BCrypt.withDefaults().hashToString(10, password.toCharArray());
    }

    public boolean checkPassword(String password) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), this.passwordHash);
        return result.verified;
    }
}


