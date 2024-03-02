package com.calero.lili.api.dtos.vtVentas;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class VtVentaCreationRequestDetailDto {

    private UUID idItem;
    private String codigoItem;
    private String item;
    private BigDecimal precioUnitario;
    private BigDecimal cantidad;
    private BigDecimal descuento;

}
