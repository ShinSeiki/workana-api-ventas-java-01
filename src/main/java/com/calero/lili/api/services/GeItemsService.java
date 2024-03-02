package com.calero.lili.api.services;
import com.calero.lili.api.dtos.geItems.GeItemCreationRequestDto;
import com.calero.lili.api.dtos.geItems.GeItemCreationResponseDto;
import com.calero.lili.api.dtos.geItems.GeItemListFilterDto;
import com.calero.lili.api.dtos.geItems.GeItemReportDto;
import com.calero.lili.api.dtos.vtClientes.VtClienteListFilterDto;
import com.calero.lili.api.repositories.projections.GeItemsProjection;
import com.calero.lili.api.repositories.projections.VtClientesProjectionPrueba;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface GeItemsService {

    GeItemCreationResponseDto create(GeItemCreationRequestDto request);
    GeItemCreationResponseDto update(UUID id, GeItemCreationRequestDto request);
}
