package com.calero.lili.api.services;

import com.calero.lili.api.dtos.vtClientes.VtClienteListFilterDto;
import com.calero.lili.api.dtos.vtClientes.VtClienteReportDto;
import com.calero.lili.api.dtos.vtClientes.VtClienteCreationRequestDto;
import com.calero.lili.api.dtos.vtClientes.VtClienteCreationResponseDto;
import com.calero.lili.api.dtos.vtClientes.VtClienteListCreationRequestDto;
import com.calero.lili.api.dtos.vtVentas.VtVentaCreationResponseDto;
import com.calero.lili.api.repositories.projections.VtClientesProjectionPrueba;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface VtClientesService {

    VtClienteCreationResponseDto create(VtClienteCreationRequestDto request);


    VtClienteCreationResponseDto update(UUID id, VtClienteCreationRequestDto request);


}
