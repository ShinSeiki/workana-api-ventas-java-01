package com.calero.lili.api.services;

import com.calero.lili.api.repositories.entities.VtClientesEntity;
import com.calero.lili.api.dtos.vtClientes.VtClienteCreationRequestDto;
import com.calero.lili.api.dtos.vtClientes.VtClienteCreationResponseDto;
import com.calero.lili.api.errors.exceptions.AlreadyExistsException;
import com.calero.lili.api.errors.exceptions.NotFoundException;
import com.calero.lili.api.repositories.projections.VtClientesProjection;
import com.calero.lili.api.repositories.VtClientesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class VtClientesServiceImpl implements VtClientesService {

    private final VtClientesRepository vtClientesRepository;

    @Override
    public VtClienteCreationResponseDto create(VtClienteCreationRequestDto request) {

            Optional<VtClientesProjection> existing = vtClientesRepository.findfirst(
                   request.getNumeroIdentificacion());
            if (existing.isPresent()) {
                throw new AlreadyExistsException(format(
                        "Data: {0} / El cliente con número de identificación {1} ya existe",
                         request.getNumeroIdentificacion()));
            }

            VtClientesEntity newEntity = toEntity(request);
            VtClientesEntity savedEntity = vtClientesRepository.save(newEntity);
            return toDto(savedEntity);
    }

    public VtClienteCreationResponseDto update(UUID id, VtClienteCreationRequestDto request) {
        VtClientesEntity entidad = vtClientesRepository.findById(id).orElseThrow(()-> new NotFoundException(format("Id {0} no exists", id)));
        entidad.setCliente(request.getCliente());
        entidad.setNumeroIdentificacion(request.getNumeroIdentificacion());
        VtClientesEntity updated = vtClientesRepository.save(entidad);
        return toDto(updated);
    }

    private VtClienteCreationResponseDto toDto(VtClientesEntity entity) {
        VtClienteCreationResponseDto dto = new VtClienteCreationResponseDto();
        dto.setIdCliente(entity.getIdCliente());
        dto.setCliente(entity.getCliente());
        dto.setNumeroIdentificacion(entity.getNumeroIdentificacion());
        return dto;
    }

    private VtClientesEntity toEntity(VtClienteCreationRequestDto request) {
        VtClientesEntity  entidad = new VtClientesEntity();
        entidad.setIdCliente(UUID.randomUUID());
        entidad.setCliente(request.getCliente());
        entidad.setNumeroIdentificacion(request.getNumeroIdentificacion());
        return entidad;
    }

}
