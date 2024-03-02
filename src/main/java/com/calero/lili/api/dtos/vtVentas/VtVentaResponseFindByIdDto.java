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
    private String secuencia;

    private String idCliente;
    private String numeroIdentificacion;

    private BigDecimal subtotal;
    private BigDecimal descuento;

    private List<VtVentaDetalleResponseDto> vtVentaDetalleResponseDtos;

}
