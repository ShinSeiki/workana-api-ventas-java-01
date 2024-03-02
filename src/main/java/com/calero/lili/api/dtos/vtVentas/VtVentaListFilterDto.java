package com.calero.lili.api.dtos.vtVentas;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class VtVentaListFilterDto {

    private UUID idFactura;
    private String secuencia;
    private String fechaEmisionDesde;
    private String fechaEmisionHasta;

}
