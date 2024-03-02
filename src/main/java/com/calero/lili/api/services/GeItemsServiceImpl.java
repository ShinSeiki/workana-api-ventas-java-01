package com.calero.lili.api.services;

import com.calero.lili.api.errors.exceptions.AlreadyExistsException;
import com.calero.lili.api.errors.exceptions.NotFoundException;
import com.calero.lili.api.errors.exceptions.ReadOnlyException;
import com.calero.lili.api.repositories.entities.*;
import com.calero.lili.api.repositories.projections.GeItemsProjection;
import com.calero.lili.api.dtos.geItems.GeItemCreationRequestDto;
import com.calero.lili.api.dtos.geItems.GeItemCreationResponseDto;
import com.calero.lili.api.dtos.geItems.GeItemListFilterDto;
import com.calero.lili.api.dtos.geItems.GeItemReportDto;
import com.calero.lili.api.repositories.GeItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class GeItemsServiceImpl implements GeItemsService {

    private final GeItemsRepository geItemsRepository;

    public GeItemCreationResponseDto create(GeItemCreationRequestDto request) {

        Optional<GeItemsProjection> existing = geItemsRepository.findfirst(request.getCodigoItem());
        if (existing.isPresent()) {
            throw new AlreadyExistsException(format(
                    "Data: {0} / Empresa {1}: El item con codigo {1} ya existe",
                     request.getCodigoItem()));
        }

        GeItemsEntity entidad = new GeItemsEntity();
        entidad.setIdItem(UUID.randomUUID());
        GeItemsEntity createdDto = geItemsRepository.save(toEntity(request, entidad));
        return toDto(createdDto);
    }

    public GeItemCreationResponseDto update(UUID id, GeItemCreationRequestDto request) {
        GeItemsEntity entidad = geItemsRepository.findById(id).orElseThrow(()-> new NotFoundException(format("Id {0} no exists", request.getIdItem())));
        GeItemsEntity updated = geItemsRepository.save(toEntity(request, entidad));
        return toDto(updated);
    }

    private GeItemsEntity toEntity(GeItemCreationRequestDto request, GeItemsEntity entidad) {

        entidad.setCodigoItem(request.getCodigoItem());
        entidad.setItem(request.getItem());
        return entidad;
    }
    private GeItemCreationResponseDto toDto(GeItemsEntity entity) {
        GeItemCreationResponseDto dto = new GeItemCreationResponseDto();
        dto.setIdItem(entity.getIdItem());
        dto.setCodigoItem(entity.getCodigoItem());
        dto.setItem(entity.getItem());
        return dto;
    }
}
