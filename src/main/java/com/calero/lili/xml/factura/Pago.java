package com.calero.lili.xml.factura;

import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"formaPago", "total", "plazo", "unidadTiempo"})
public class Pago {
    private String formaPago;
    private String total;

    private String unidadTiempo;
    private String plazo;

    public String getUnidadTiempo() {
        return unidadTiempo;
    }

    public void setUnidadTiempo(String unidadTiempo) {
        this.unidadTiempo = unidadTiempo;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
