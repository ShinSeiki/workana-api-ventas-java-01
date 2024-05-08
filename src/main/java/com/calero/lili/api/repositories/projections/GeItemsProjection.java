package com.calero.lili.api.repositories.projections;

import com.calero.lili.api.repositories.entities.GeItemsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;
import java.util.UUID;

public interface GeItemsProjection {

    UUID getIdItem();
    void setIdItem(UUID idItem);

    String getCodigoItem();
    void setCodigoItem(String codigoItem);

    String getCodigoBarras();
    void setCodigoBarras(String codigoBarras);

    String getItem();
    void setItem(String item);

    float getPrecioUnitario();
    void getPrecioUnitario(float precioUnitario);

    float getDescuentoPorcentaje();
    void getDescuentoPorcentaje(float descuentoPorcentaje);

    float getDescuentoValor();
    void getDescuentoValor(float descuentoValor);

    //String getDetallesAdicionales();
    //void setDetallesAdicionales(String detallesAdicionales);

    List<GeItemsEntity.DetalleAdicional> getDetallesAdicionales();
    void setDetallesAdicionales(List detalleAdicional);

    List<GeItemsEntity.Impuesto> getImpuestos();
    void setImpuestos(List impuesto);

}

