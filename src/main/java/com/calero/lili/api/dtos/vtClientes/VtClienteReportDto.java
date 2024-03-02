package com.calero.lili.api.dtos.vtClientes;

import com.calero.lili.api.repositories.entities.VtClientesEntity;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class VtClienteReportDto {

    private UUID idCliente;

    private String cliente;

    private String numeroIdentificacion;

    private UUID idGrupo;

}
