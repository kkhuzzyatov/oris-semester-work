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

    public static final String ERROR_EMPTY_NAME = "Имя не может быть пустым";
    public static final String ERROR_INVALID_EMAIL = "Некорректный email: ";
    public static final String ERROR_INVALID_PHONE = "Некорректный телефон: ";
    public static final String ERROR_INVALID_PASSWORD = "Некорректный пароль: ";

    // Name validation
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(ERROR_EMPTY_NAME);
        }
        this.name = name;
    }

    // Email validation
    public void setEmail(String email) {
        if (email == null || !email.matches("^[a-z0-9]+@(gmail|yandex|mail|list)\\.(com|ru)$")) {
            throw new IllegalArgumentException(ERROR_INVALID_EMAIL + email);
        }
        this.email = email;
    }

    // Phone validation
    public void setPhone(String phone) {
        if (phone == null || !phone.matches("^8\\d{10}$")) {
            throw new IllegalArgumentException(ERROR_INVALID_PHONE + phone);
        }
        this.phone = phone;
    }

    // Password validation + hashing
    public void setPassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException(ERROR_INVALID_PASSWORD + password);
        }
        this.passwordHash = BCrypt.withDefaults().hashToString(10, password.toCharArray());
    }

    public boolean checkPassword(String password) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), this.passwordHash);
        return result.verified;
    }
}

