package ru.orissemesterwork.api.user.model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoResponse {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private Integer cityId;
    private Integer pointId;
}