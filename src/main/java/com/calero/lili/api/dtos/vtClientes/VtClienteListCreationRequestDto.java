package com.calero.lili.api.dtos.vtClientes;

import lombok.Data;

import java.util.List;

@Data
public class VtClienteListCreationRequestDto {

    private List<VtClienteListCreationRequestDetailDto> listaClientes;

}
