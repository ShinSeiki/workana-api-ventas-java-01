package com.calero.lili.xml;

import com.calero.lili.xml.factura.Factura;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlToObject {
    public static void main(String[] args) throws JAXBException {

        File file = new File("Factura.xml");

        JAXBContext jaxbContext = JAXBContext.newInstance(Factura.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Factura fc = (Factura) jaxbUnmarshaller.unmarshal(file);
        System.out.println("Si se pudo leer el XML y convertirlo en Objeto Factura: "+fc.getInfoFactura().getComercioExterior());

    }

}
