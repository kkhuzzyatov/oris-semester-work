package ru.orissemesterwork.api.user.model.service;

import ru.orissemesterwork.api.user.model.dto.UserDtoRequest;
import ru.orissemesterwork.api.user.model.dto.UserDtoResponse;

public interface UserService {
    UserDtoResponse register(UserDtoRequest userDtoRequest);
    UserDtoResponse login(String email, String password);
}