package com.calero.lili.xml.factura;

import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"codigo", "codigoPorcentaje", "tarifa", "baseImponibleReembolso", "impuestoReembolso"})
public class DetalleImpuesto {
    private String codigo;
    private String codigoPorcentaje;
    private String tarifa;
    private String baseImponibleReembolso;
    private String impuestoReembolso;
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoPorcentaje() {
        return codigoPorcentaje;
    }

    public void setCodigoPorcentaje(String codigoPorcentaje) {
        this.codigoPorcentaje = codigoPorcentaje;
    }

    public String getTarifa() {
        return tarifa;
    }

    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }

    public String getBaseImponibleReembolso() {
        return baseImponibleReembolso;
    }

    public void setBaseImponibleReembolso(String baseImponibleReembolso) {
        this.baseImponibleReembolso = baseImponibleReembolso;
    }

    public String getImpuestoReembolso() {
        return impuestoReembolso;
    }

    public void setImpuestoReembolso(String impuestoReembolso) {
        this.impuestoReembolso = impuestoReembolso;
    }
}
