package com.calero.lili.xml.factura;

import javax.xml.bind.annotation.XmlAttribute;

public class DetAdicional {
    private String nombre;
    private String valor;
    
    @XmlAttribute(name = "nombre")    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlAttribute(name = "valor")    
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    
}
