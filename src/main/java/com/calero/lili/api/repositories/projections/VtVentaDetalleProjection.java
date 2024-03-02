package com.calero.lili.api.repositories.projections;

import java.math.BigDecimal;
import java.util.UUID;

public interface VtVentaDetalleProjection {

    UUID getIdVenta();
    void setIdVenta(String idVenta);
    String getSerie();
    void setSerie(String serie);
    String getSecuencia();
    void setSecuencia(String secuencia);

    String getCodigoItem();
    void setCodigoItem(String codigoItem);

    String getItem();
    void setItem(String item);

    BigDecimal getPrecioUnitario();
    void setPrecioUnitario(BigDecimal precioUnitario);

    Long getInvoiceId();
    void setInvoiceId(Long invoiceId);

    String getSupplierNumber();
    void setSupplierNumber(String supplierNumber);

    String getSupplierName();
    void setSupplierName(String supplierName);

    String getIdCliente();
    void setIdCliente(String idCliente);

    String getCliente();
    void setCliente(String cliente);
}
