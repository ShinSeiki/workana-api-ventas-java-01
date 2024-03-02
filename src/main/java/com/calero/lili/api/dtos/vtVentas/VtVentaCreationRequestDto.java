package com.calero.lili.api.dtos.vtVentas;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class VtVentaCreationRequestDto {

//    private VtVentaCreationRequestClienteDto clienteDatos;

    private UUID idCliente;
    private String secuencia;
    private String fechaEmision;
    private String numeroIdentificacion;

    private BigDecimal total;

    private List<VtVentaCreationRequestDetailDto> detalleItems;

}
