package com.calero.lili.xml.factura;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class CampoAdicional {
    private String nombre;
    private String valor;

    @XmlAttribute(name = "nombre")    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @XmlValue // pierde el nombre propio...
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
}
