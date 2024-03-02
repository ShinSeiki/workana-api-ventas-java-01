package com.calero.lili.api.dtos.vtClientes;

import com.calero.lili.api.repositories.entities.VtClientesEntity;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Data
public class VtClienteCreationRequestDto {

    private String cliente;

    private String numeroIdentificacion;

    private UUID idGrupo;

}
