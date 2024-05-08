package com.calero.lili.api.dtos.vtClientes;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class VtClienteFilterDto {

    private String filter;

    private String tipoIdentificacion;

    private UUID idGrupo;

}
