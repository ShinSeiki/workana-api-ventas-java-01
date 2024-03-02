package com.calero.lili.api.dtos.vtClientes;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class VtClienteListFilterDto {

    private String filter;

    private UUID idGrupo;

}
