package ru.orissemesterwork.api.user.model.repository;

import ru.orissemesterwork.api.user.model.entity.User;

public interface UserRepository {
    void save(User user);
    User findById(Integer id);
    User findByEmail(String email);
    User findByPhone(String phone);
}
