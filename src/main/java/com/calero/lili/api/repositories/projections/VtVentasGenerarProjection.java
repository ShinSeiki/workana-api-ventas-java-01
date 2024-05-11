package com.calero.lili.api.repositories.projections;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface VtVentasGenerarProjection {


    String getIdSucursal();
    void setIdSucursal(String idSucursal);

    UUID getIdVenta();
    void setIdVenta(UUID idVenta);

    String getTipoVenta();
    void setTipoVenta(String tipoVenta);

    String getSerie();
    void setSerie(String serie);

    String getSecuencia();
    void setSecuencia(String secuencia);

    String getAutorizacionSri();
    void setAutorizacionSri(String autorizacionSri);

    LocalDate getFechaEmision();
    void setFechaEmision(LocalDate fechaEmision);

    String getIdCliente();
    void setIdCliente(String idCliente);

    String getCliente();
    void setCliente(String cliente);

    String getModificadaTipoVenta();
    void setModificadaTipoVenta(String modificadaTipoVenta);

    String getModificadaSerie();
    void setModificadaSerie(String modificadaSerie);

    String getModificadaSecuencia();
    void setModificadaSecuencia(String modificadaSecuencia);

    //String getIdCliente();
    //void setIdCliente(String idCliente);

    String getTipoIdentificacion();
    void setTipoIdentificacion(String tipoIdentificacion);

    String getNumeroIdentificacion();
    void setNumeroIdentificacion(String numeroIdentificacion);

    String getRelacionado();
    void setRelacionado(String relacionado);

    String getTipoCliente();
    void setTipoCliente(String tipoCliente);

    String getMail();
    void setMail(String mail);

    String getConcepto();
    void setConcepto(String concepto);

    BigDecimal getBaseCero();
    void setBaseCero(BigDecimal baseCero);

    BigDecimal getBaseGravada1();
    void setBaseGravada1(BigDecimal baseGravada1);

    BigDecimal getPorcentajeIva1();
    void setPorcentajeIva1(BigDecimal porcentajeIva1);

    BigDecimal getIva1();
    void setIva1(BigDecimal iva1);

    BigDecimal getBaseGravada2();
    void setBaseGravada2(BigDecimal baseGravada2);

    BigDecimal getPorcentajeIva2();
    void setPorcentajeIva2(BigDecimal porcentajeIva2);

    BigDecimal getIva2();
    void setIva2(BigDecimal iva2);

    BigDecimal getBaseNoObjeto();
    void setBaseNoObjeto(BigDecimal baseNoObjeto);

    BigDecimal getBaseExenta();
    void setBaseExenta(BigDecimal baseExenta);

    BigDecimal getTdscto();
    void setTdscto(BigDecimal tdscto);

    BigDecimal getTdescuento();
    void setTdescuento(BigDecimal tdescuento);

    BigDecimal getTotal();
    void setTotal(BigDecimal total);

    String getTipodoc();
    void setTipodoc(String tipodoc);

    String getTipo();
    void setTipo(String tipo);

    String getCodigoDocumento();
    void setCodigoDocumento(String codigoDocumento);

    BigDecimal getItems();
    void setItems(BigDecimal items);

    LocalDate getFechaVencimiento();
    void setFechaVencimiento(LocalDate fechaVencimiento);

    LocalDate getFechaanu();
    void setFechaanu(LocalDate fechaanu);

    String getAgenteRetencion();
    void setAgenteRetencion(String agenteRetencion);

    String getContacto();
    void setContacto(String contacto);

    Integer getDiasCredito();
    void setDiasCredito(Integer diasCredito);

    Integer getCuotas();
    void setCuotas(Integer cuotas);

    Integer getCzona();
    void setCzona(Integer czona);

    String getTipoContribuyente();
    void setTipoContribuyente(String tipoContribuyente);

    String getDocumentoElectronico();
    void setDocumentoElectronico(String documentoElectronico);

    String getTipoEmision();
    void setTipoEmision(String tipoEmision);

    String getAmbiente();
    void setAmbiente(String ambiente);

    String getEstadoDocumento();
    void setEstadoDocumento(String estadoDocumento);

    String getClaveAcceso();
    void setClaveAcceso(String claveAcceso);

    LocalDate getFechaAutorizacion();
    void setFechaAutorizacion(String fechaAutorizacion);

    String getMensaje();
    void setMensaje(String mensaje);

    String getXml();
    void setXml(String xml);

    String getEmailEstado();
    void setEmailEstado(String emailEstado);

    Integer getIdVendedor();
    void setIdVendedor(Integer idVendedor);

    Boolean getAnulada();
    void setAnulada(Boolean anulada);

    Boolean getImpresa();
    void setImpresa(Boolean impresa);

}
