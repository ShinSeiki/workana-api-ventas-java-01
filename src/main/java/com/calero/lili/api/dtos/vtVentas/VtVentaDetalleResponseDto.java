package com.calero.lili.api.dtos.vtVentas;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class VtVentaDetalleResponseDto {

    private UUID idItem;
    private String codigoItem;
    private String item;
    private BigDecimal precioUnitario;
    private BigDecimal cantidad;
    private BigDecimal taxAmount;
    private BigDecimal descuento;
    private BigDecimal subTotal;

}
