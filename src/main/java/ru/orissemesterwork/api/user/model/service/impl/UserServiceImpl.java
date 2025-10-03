package ru.orissemesterwork.api.user.model.service.impl;

import ru.orissemesterwork.api.user.model.dto.UserDtoRequest;
import ru.orissemesterwork.api.user.model.dto.UserDtoResponse;
import ru.orissemesterwork.api.user.model.entity.User;
import ru.orissemesterwork.api.user.model.mapper.UserMapper;
import ru.orissemesterwork.api.user.model.repository.UserRepository;
import ru.orissemesterwork.api.user.model.service.UserService;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDtoResponse register(UserDtoRequest userDtoRequest) {
        if (userRepository.findByEmail(userDtoRequest.getEmail()) != null) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует: " + userDtoRequest.getEmail());
        }
        User user = UserMapper.toEntity(userDtoRequest);
        userRepository.save(user);
        return UserMapper.toResponse(user);
    }

    @Override
    public UserDtoResponse login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("Пользователь с таким email не найден: " + email);
        }
        if (!user.checkPassword(password)) {
            throw new IllegalArgumentException("Неверный пароль");
        }
        return UserMapper.toResponse(user);
    }
}
