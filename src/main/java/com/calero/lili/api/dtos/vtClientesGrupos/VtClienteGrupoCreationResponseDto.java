package com.calero.lili.api.dtos.vtClientesGrupos;

import lombok.Data;

import java.util.UUID;

@Data
public class VtClienteGrupoCreationResponseDto {

    private UUID idGrupo;

    private String grupo;
}
