package ru.orissemesterwork.api.user.model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoRequest {
    private String name;
    private String email;
    private String phone;
    private String password;
    private Integer cityId;
    private Integer pointId;
}
