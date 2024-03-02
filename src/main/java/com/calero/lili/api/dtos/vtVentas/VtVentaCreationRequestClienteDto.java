package com.calero.lili.api.dtos.vtVentas;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class VtVentaCreationRequestClienteDto {

    private UUID idCliente;
    private String cliente;
    private String numeroIdentificacion;

}
