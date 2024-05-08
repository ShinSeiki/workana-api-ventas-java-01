package com.calero.lili.api.dtos.vtVentas;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class VtVentaCreationResponseDto {

    private UUID idFactura;
    private BigDecimal iva;
    private BigDecimal descuento;
    private BigDecimal total;

}
