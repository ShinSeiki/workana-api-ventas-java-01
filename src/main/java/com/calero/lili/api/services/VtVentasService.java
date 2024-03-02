package com.calero.lili.api.services;

import com.calero.lili.api.dtos.vtVentas.VtVentaCreationRequestDto;
import com.calero.lili.api.dtos.vtVentas.VtVentaCreationResponseDto;

import java.util.UUID;

public interface VtVentasService {

    VtVentaCreationResponseDto create(VtVentaCreationRequestDto request);

    VtVentaCreationResponseDto update(UUID id, VtVentaCreationRequestDto request);

}
