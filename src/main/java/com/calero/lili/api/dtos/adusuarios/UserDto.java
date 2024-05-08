package com.calero.lili.api.dtos.adusuarios;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private boolean admin;

    public UserDto(Long id, String username, String email, boolean admin) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.admin = admin;
    }
    public UserDto() {
    }

}
