package com.calero.lili.api.dtos.errors;

import lombok.Data;

import java.util.List;

@Data
public class ListCreationResponseDto {

    private String respuesta;

    private List<DetallesErrores> detallesErrores;



}
