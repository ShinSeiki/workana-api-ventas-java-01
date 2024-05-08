package com.calero.lili.api.dtos.vtVentas;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VtVentaCreationRequestFormasPagoDto {

    private String formaPago;
    private BigDecimal valor;
    private String plazo;
    private String unidadTiempo;

}
