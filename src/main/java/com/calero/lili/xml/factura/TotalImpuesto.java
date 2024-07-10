package com.calero.lili.xml.factura;

import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"codigo", "codigoPorcentaje",  "baseImponible", "tarifa", "valor"})
public class TotalImpuesto {
    
    private String codigo;
    private String codigoPorcentaje;
    private String baseImponible;
    private String tarifa;
    private String valor;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(String baseImponible) {
        this.baseImponible = baseImponible;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
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
    
    
}
