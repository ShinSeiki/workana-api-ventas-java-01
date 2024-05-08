package com.calero.lili.api.dtos.adusuarios;

import lombok.Data;

@Data
public class UserReportDto {

    private Long id;

    private String username;

    private String email;

    private boolean admin;

}
