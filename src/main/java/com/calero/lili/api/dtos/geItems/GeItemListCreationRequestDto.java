package com.calero.lili.api.dtos.geItems;

import lombok.Data;

import java.util.List;

@Data
public class GeItemListCreationRequestDto {

    private List<GeItemCreationRequestDto> listaItems;

}
