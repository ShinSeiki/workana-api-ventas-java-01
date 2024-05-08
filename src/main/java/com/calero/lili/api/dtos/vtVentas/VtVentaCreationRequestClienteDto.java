package com.calero.lili.api.dtos.vtVentas;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class VtVentaCreationRequestClienteDto {

    private String cliente;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String telefonos;
    private String direccion;
    private String tipoCliente;
    private String relacionado;
    private String email;

}
