package com.calero.lili.api.dtos.geItems;

import com.calero.lili.api.repositories.entities.GeItemsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;
import java.util.UUID;

@Data
public class GeItemReportDto {

    private UUID idItem;
    private String codigoItem;
    private String codigoBarras;
    private String item;
    private String cmarca;
    private String cmedida;
    private List<GeItemsEntity.DetalleAdicional> detallesAdicionales;
    private List<GeItemsEntity.Impuesto> impuestos;
    private int idMedida;
    private Integer idGrupo;

}
