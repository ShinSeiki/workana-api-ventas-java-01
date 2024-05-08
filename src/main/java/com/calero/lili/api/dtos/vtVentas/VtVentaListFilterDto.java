package com.calero.lili.api.dtos.vtVentas;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
public class VtVentaListFilterDto {

    private UUID idFactura;
    private String tipoVenta;
    private String serie;
    private String secuencia;
    private String fechaEmisionDesde;
    private String fechaEmisionHasta;
    private String numeroIdentificacion;
    private String claveAcceso;
    /*
    private String autorizacionSri;
    private Timestamp fechaEmision;
    private String modificadaTipoVenta;
    private String modificadaSerie;
    private String modificadaSecuencia;
    private String idCliente;
    private String tipoIdentificacion;

    private Boolean relacionado;
    private String tipoCliente;
    private String mail;
    private String concepto;
    private BigDecimal basimpng;
    private BigDecimal poriva;
    private BigDecimal bbasimpsg;
    private BigDecimal bvaliva;
    private BigDecimal sbasimpsg;
    private BigDecimal svaliva;
    private BigDecimal basimpnob;
    private BigDecimal tdscto;
    private BigDecimal tdescuento;
    private BigDecimal total;
    private String tipodoc;
    private String tipo;
    private String codigoDocumento;
    private BigDecimal items;
    private Timestamp fechaVencimiento;
    private Timestamp fechaanu;
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

    private Timestamp fechaAutorizacion;
    private String mensaje;
    private String xml;
    private String emailEstado;
    private String formasPagoSri;
    private Integer idVendedor;

     */
    private Boolean anulada;
    private Boolean impresa;

}
