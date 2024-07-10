package com.calero.lili.xml.factura;

import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"motivoTraslado","ruta"})

public class Destino {
    private String motivoTraslado;
    private String ruta;

    public String getMotivoTraslado() {
        return motivoTraslado;
    }

    public void setMotivoTraslado(String motivoTraslado) {
        this.motivoTraslado = motivoTraslado;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}
