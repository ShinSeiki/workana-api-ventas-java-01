package com.calero.lili.api.dtos.vtVentas;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class VtVentaDetalleSummaryDto {

    private UUID idVenta;
    private String secuencia;
    private String idCliente;
    private String cliente;
    private String numeroIdentificacion;
    private String codigoItem;
    private String item;
    private BigDecimal precioUnitario;

}
