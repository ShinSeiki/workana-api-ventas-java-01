package com.calero.lili.api.dtos.vtVentas;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class VtVentaResponseFindByIdDto {

    private UUID idVenta;

    private String fechaEmision;
    private String serie;
    private String secuencia;

    private String idCliente;
    private String numeroIdentificacion;
    private String telefonos;
    private List<String> emails;

    private BigDecimal subtotal;
    private BigDecimal descuento;

    private BigDecimal baseCero;
    private BigDecimal baseGravada;
    private BigDecimal iva;
    private BigDecimal baseExenta;
    private BigDecimal baseNoObjeto;

    private List<VtVentaDetalleResponseDto> vtVentaDetalleResponseDtos;

}
