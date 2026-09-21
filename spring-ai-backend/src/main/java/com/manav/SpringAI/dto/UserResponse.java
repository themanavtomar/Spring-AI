package com.manav.SpringAI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {

    private String id;
    private String email;
    private String authProvider;
    private String name;
    private String avatar;
    private Integer age;
    private String profession;
    private String company;
}