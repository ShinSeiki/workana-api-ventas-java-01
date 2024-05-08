package com.calero.lili.api.dtos.vtVentas;

import lombok.Data;

@Data
public class VtVentaCreationRequestDatosFacturaModificadaDto {

    private String motivo;
    private String modificadaTipoVenta;
    private String modificadaSerie;
    private String modificadaSecuencia;

}
