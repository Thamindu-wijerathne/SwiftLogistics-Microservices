package com.cms.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private final Long id;
    private final String name;
    private final String password;
    private final String email;
    private final String gender;

    // Constructor
    public UserDTO(Long id, String email, String name, String password, String gender) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.gender = gender;
        ;
    }
}







