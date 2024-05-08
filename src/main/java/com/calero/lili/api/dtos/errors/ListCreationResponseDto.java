package com.calero.lili.api.dtos.errors;

import com.calero.lili.api.dtos.errors.DetallesErrores;
import lombok.Data;

import java.util.List;

@Data
public class ListCreationResponseDto {

    private String respuesta;

    private List<DetallesErrores> detallesErrores;

}
