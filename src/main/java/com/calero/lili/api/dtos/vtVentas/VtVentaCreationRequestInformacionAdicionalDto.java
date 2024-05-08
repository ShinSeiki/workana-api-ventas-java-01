package com.calero.lili.api.dtos.vtVentas;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class VtVentaCreationRequestInformacionAdicionalDto {

    private String nombre;
    private String valor;

}
