package com.calero.lili.api.dtos.vtClientesGrupos;

import lombok.Data;

import java.util.UUID;

@Data
public class VtClienteGrupoCreationRequestDto {

    private UUID idGrupo;

    private String grupo;

}
