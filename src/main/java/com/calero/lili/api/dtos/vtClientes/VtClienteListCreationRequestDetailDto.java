package com.calero.lili.api.dtos.vtClientes;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class VtClienteListCreationRequestDetailDto {
    private String cliente;
    private String numeroIdentificacion;
}
