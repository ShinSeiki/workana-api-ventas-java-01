package com.calero.lili.api.dtos.vtVentas;

import com.calero.lili.api.repositories.entities.VtVentaEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class VtVentaCreationRequestDto {

    private String idSucursal;
    private UUID idFactura;
    private String tipoVenta;
    private String serie;
    private String secuencia;
    private String autorizacionSri;
    private String fechaEmision;
    private UUID idCliente;
//    private VtNotaCreationRequestClienteDto clienteDatos;
    private VtVentaCreationRequestClienteDto clienteDatos; // no es una lista es un solo objeto
    private VtVentaCreationRequestDatosFacturaModificadaDto datosFacturaModificada; // no es una lista es un solo objeto

    private String concepto;

    private BigDecimal subTotal;
    private BigDecimal descuento;

    private BigDecimal baseCero;

    private BigDecimal baseGravada1;
    private int porcentajeIva1;
    private BigDecimal iva1;

    private BigDecimal baseGravada2;
    private int porcentajeIva2;
    private BigDecimal iva2;

    private BigDecimal baseNoObj;
    private BigDecimal baseExenta;

    private BigDecimal total;

    private String tipodoc;
    private String tipo;
    private String codigoDocumento;
    private Integer items;
    private String fechaVencimiento;
    private String fechaanu;
    private Integer diasCredito;
    private Integer cuotas;
    private Integer czona;
    private String documentoElectronico;
    private String emailEstado;
    private Integer idVendedor;
    private Boolean anulada;
    private Boolean impresa;

    private List<VtVentaCreationRequestDetailDto> detalleItems;
    private List<VtVentaCreationRequestInformacionAdicionalDto> informacionAdicional;
    private List<VtVentaCreationRequestFormasPagoDto> formasPagoSri;

}
