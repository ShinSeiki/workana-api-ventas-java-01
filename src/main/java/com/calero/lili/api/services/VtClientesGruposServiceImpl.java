package com.calero.lili.api.services;

import com.calero.lili.api.dtos.vtClientesGrupos.VtClienteGrupoCreationRequestDto;
import com.calero.lili.api.dtos.vtClientesGrupos.VtClienteGrupoCreationResponseDto;
import com.calero.lili.api.dtos.vtClientesGrupos.VtClienteGrupoListFilterDto;
import com.calero.lili.api.dtos.vtClientesGrupos.VtClienteGrupoReportDto;
import com.calero.lili.api.errors.exceptions.AlreadyExistsException;
import com.calero.lili.api.errors.exceptions.NotFoundException;
import com.calero.lili.api.errors.exceptions.ReadOnlyException;
import com.calero.lili.api.repositories.entities.GeItemsEntity;
import com.calero.lili.api.repositories.entities.VtClientesGruposEntity;
import com.calero.lili.api.repositories.projections.VtClientesGruposProjection;
import com.calero.lili.api.repositories.VtClientesGruposRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class VtClientesGruposServiceImpl implements VtClientesGruposService {

    private final VtClientesGruposRepository vtClientesGruposRepository ;
    public VtClienteGrupoCreationResponseDto create(VtClienteGrupoCreationRequestDto request) {
        VtClientesGruposEntity entidad = new VtClientesGruposEntity();
        entidad.setIdGrupo(UUID.randomUUID());
        VtClientesGruposEntity createdDto = vtClientesGruposRepository.save(toEntity(request, entidad));
        return toDto(createdDto);
    }

    public VtClienteGrupoCreationResponseDto update(UUID id, VtClienteGrupoCreationRequestDto request) {
        VtClientesGruposEntity entidad = vtClientesGruposRepository.findById(id).orElseThrow(()-> new NotFoundException(format("Id {0} no exists", request.getIdGrupo())));
        VtClientesGruposEntity updated = vtClientesGruposRepository.save(toEntity(request, entidad));
        return toDto(updated);
    }


    private VtClientesGruposEntity toEntity(VtClienteGrupoCreationRequestDto request, VtClientesGruposEntity entidad) {
        entidad.setGrupo(request.getGrupo());
    return entidad;
    }
    private VtClienteGrupoCreationResponseDto toDto(VtClientesGruposEntity entity) {
        VtClienteGrupoCreationResponseDto dto = new VtClienteGrupoCreationResponseDto();
        dto.setIdGrupo(entity.getIdGrupo());
        dto.setGrupo(entity.getGrupo());
        return dto;
    }
}
