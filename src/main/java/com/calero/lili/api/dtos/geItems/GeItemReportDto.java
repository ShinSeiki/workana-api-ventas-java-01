package com.calero.lili.api.dtos.geItems;

import lombok.Data;

import java.util.UUID;

@Data
public class GeItemReportDto {

    private UUID idItem;
    private String codigoItem;
    private String codigoBarras;
    private String item;
    private Integer idGrupo;

}
