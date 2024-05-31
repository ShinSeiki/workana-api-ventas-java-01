package com.calero.lili.api.services;

import com.calero.lili.api.dtos.adDatas.AdDataCreationRequestDto;
import com.calero.lili.api.dtos.adDatas.AdDataCreationResponseDto;
import com.calero.lili.api.dtos.adDatas.AdDataListFilterDto;
import com.calero.lili.api.dtos.adDatas.AdDataReportDto;
import com.calero.lili.api.errors.exceptions.AlreadyExistsException;
import com.calero.lili.api.errors.exceptions.NotFoundException;
import com.calero.lili.api.errors.exceptions.ReadOnlyException;
import com.calero.lili.api.repositories.AdDataRepository;
import com.calero.lili.api.repositories.entities.AdDatasEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class AdDatasServiceImpl {

    private final AdDataRepository adDataRepository;
    
    public AdDataCreationResponseDto create(String id, AdDataCreationRequestDto request) {
            Optional<AdDatasEntity> existing = adDataRepository.findById(id);
            if (existing.isPresent()) {
                throw new AlreadyExistsException(MessageFormat.format("Data {0} ya existe", id));
            }
            return toDto(adDataRepository.save(toEntity(id, request)));
    }

    public AdDataCreationResponseDto update(String id, AdDataCreationRequestDto request) {
        AdDatasEntity entidad = adDataRepository.findByIdData(id);
        if (entidad != null) {
            entidad = adDataRepository.save(toEntity(id, request));
        } else {
            throw new ReadOnlyException(format("Id {0} no existe", id));
        }
        return toDto(entidad);
    }

    public AdDataCreationResponseDto findByIdData(String idData) {
        AdDatasEntity entidad = adDataRepository.findById(idData).orElseThrow(() -> new NotFoundException(format("Id {0} no exists",  idData )));
        if (!entidad.getIdData().equals(idData)){
            throw new ReadOnlyException(format("Id {1} no exists", idData));
        }
        return toDto(entidad);
    }

    public Page<AdDataReportDto> findAllPaginate(AdDataListFilterDto filters, Pageable pageable) {
        Page<AdDatasEntity> page = adDataRepository.findAllPaginate(filters.getFilter(), pageable);
        return new PageImpl<>(
                page.getContent()
                        .stream()
                        .map(this::toReport)
                        .collect(Collectors.toList()),
                page.getPageable(),
                page.getTotalElements());
    }

    private AdDataReportDto toReport(AdDatasEntity entity) {
        AdDataReportDto dto = new AdDataReportDto();
        dto.setIdData(entity.getIdData());
        dto.setData(entity.getData());
        return dto;
    }

    private  AdDatasEntity findFirstById(String id) {
        return adDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(format("Unable to find id {0}", id)));
    }
    private  AdDatasEntity toEntity(String id, AdDataCreationRequestDto request) {
        AdDatasEntity entidad = new  AdDatasEntity();
        entidad.setIdData(id);
        entidad.setData(request.getData());

        //entidad.setBirthday(DateUtils.toLocalDate(request.getBirthday()));
        //entidad.setInsertionDate(LocalDateTime.now());
        //entidad.setIdentificationNumber(request.getIdentificationNumber());
        return entidad;
    }
    private AdDataCreationResponseDto toDto(AdDatasEntity entity) {
        AdDataCreationResponseDto dto = new AdDataCreationResponseDto();
        dto.setIdData(entity.getIdData());
        dto.setData(entity.getData());
        //dto.setId(entity.getId());
        return dto;
    }

}
