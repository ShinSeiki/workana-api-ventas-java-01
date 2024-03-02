package com.calero.lili.api.dtos.geItems;

import com.calero.lili.api.repositories.entities.GeItemsEntity;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class GeItemCreationResponseDto {

    private UUID idItem;
    private String codigoItem;
    private String item;
    private Integer idGrupo;

}
