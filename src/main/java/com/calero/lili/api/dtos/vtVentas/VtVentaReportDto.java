package com.calero.lili.api.dtos.vtVentas;

import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class VtVentaReportDto {

    private UUID idVenta;
    private String secuencia;
    private LocalDate fechaEmision;

    private String idCliente;
    private String cliente;
    private String numeroIdentificacion;

    private BigDecimal total;

    private BigDecimal items;

}
