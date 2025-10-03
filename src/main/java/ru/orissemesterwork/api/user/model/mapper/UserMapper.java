package ru.orissemesterwork.api.user.model.mapper;

import ru.orissemesterwork.api.user.model.dto.UserDtoRequest;
import ru.orissemesterwork.api.user.model.dto.UserDtoResponse;
import ru.orissemesterwork.api.user.model.entity.User;

public class UserMapper {
    private UserMapper() {

    }

    public static User toEntity(UserDtoRequest dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setPassword(dto.getPassword());
        user.setCityId(dto.getCityId());
        user.setPointId(dto.getPointId());
        return user;
    }

    public static UserDtoResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        UserDtoResponse response = new UserDtoResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setCityId(user.getCityId());
        response.setPointId(user.getPointId());
        return response;
    }
}
