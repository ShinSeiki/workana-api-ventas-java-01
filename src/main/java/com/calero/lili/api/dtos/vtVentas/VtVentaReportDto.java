package com.calero.lili.api.dtos.vtVentas;

import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class VtVentaReportDto {

    private String idSucursal;
    private UUID idVenta;
    private String tipoVenta;
    private String serie;
    private String secuencia;
    private String autorizacionSri;
    private LocalDate fechaEmision;

    private String idCliente;
    private String cliente;
    private String numeroIdentificacion;

    private String relacionado;
    private String tipoCliente;
    private String mail;
    private String concepto;

    private String tipodoc;
    private String tipo;
    private String codigoDocumento;
    private LocalDate fechaVencimiento;
    private LocalDate fechaanu;

    private BigDecimal baseCero;
    private BigDecimal baseGravada1;
    private BigDecimal porcentajeIva1;
    private BigDecimal iva1;
    private BigDecimal baseGravada2;
    private BigDecimal porcentajeIva2;
    private BigDecimal iva2;
    private BigDecimal baseExenta;
    private BigDecimal baseNoObjeto;
    private BigDecimal tdscto;
    private BigDecimal tdescuento;
    private BigDecimal total;

    private BigDecimal items;

    private String agenteRetencion;
    private String contacto;
    private Integer diasCredito;
    private Integer cuotas;
    private Integer czona;
    private String tipoContribuyente;

    private String documentoElectronico;
    private String tipoEmision;
    private String ambiente;
    private String estadoDocumento;
    private String claveAcceso;
    private LocalDate fechaAutorizacion;
    private String mensaje;
    private String xml;
    private String emailEstado;
    //private String formasPagoSri;
    private Integer idVendedor;

    private String modificadaTipoVenta;
    private String modificadaSerie;
    private String modificadaSecuencia;

    private Boolean anulada;
    private Boolean impresa;

}
