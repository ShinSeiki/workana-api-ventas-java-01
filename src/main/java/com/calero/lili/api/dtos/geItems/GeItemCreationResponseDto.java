package com.calero.lili.api.dtos.geItems;

import com.calero.lili.api.repositories.entities.GeItemsEntity;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class GeItemCreationResponseDto {

    private UUID idItem;
    private String codigoItem;
    private String codigoBarras;
    private String item;
    private String cmarca;
    private String cmedida;
    //private int idMedida;
    private Integer idGrupo;

    private List<GeItemsEntity.DetalleAdicional> detallesAdicionales;
    private List<GeItemsEntity.Impuesto> impuestos;

}
