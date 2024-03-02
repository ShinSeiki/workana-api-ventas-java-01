package com.calero.lili.api.services;

import com.calero.lili.api.dtos.vtClientesGrupos.VtClienteGrupoCreationRequestDto;
import com.calero.lili.api.dtos.vtClientesGrupos.VtClienteGrupoCreationResponseDto;
import com.calero.lili.api.dtos.vtClientesGrupos.VtClienteGrupoListFilterDto;
import com.calero.lili.api.dtos.vtClientesGrupos.VtClienteGrupoReportDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface VtClientesGruposService {

    VtClienteGrupoCreationResponseDto create(VtClienteGrupoCreationRequestDto request);
    VtClienteGrupoCreationResponseDto update(UUID id, VtClienteGrupoCreationRequestDto request);

}
