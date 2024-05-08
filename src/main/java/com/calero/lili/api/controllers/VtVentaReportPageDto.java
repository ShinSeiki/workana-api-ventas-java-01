package com.calero.lili.api.controllers;

import com.calero.lili.api.dtos.vtVentas.VtVentaReportDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
public class VtVentaReportPageDto {
    private Page<VtVentaReportDto> page;
    private BigDecimal totalBaseCero;
    private BigDecimal totalBaseGravada1;
    private BigDecimal totalIva1;
    private BigDecimal totalBaseGravada2;
}
