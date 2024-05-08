package com.calero.lili.api.dtos.adusuarios;

import com.calero.lili.api.repositories.entities.adUsuario;


public class DtoMapperUser {

    private adUsuario user;
    
    private DtoMapperUser() {
    }

    public static DtoMapperUser builder() {
        return new DtoMapperUser();
    }

    public DtoMapperUser setUser(adUsuario user) {
        this.user = user;
        return this;
    }

    public UserDto build() {
        if (user == null) {
            throw new RuntimeException("Debe pasar el entity user!");
        }
        boolean isAdmin = user.getRoles().stream().anyMatch(r -> "ROLE_ADMIN".equals(r.getName()));
        return new UserDto(this.user.getId(), user.getUsername(), user.getEmail(), isAdmin);
    }
    

}
