package com.calero.lili.api.services;

import com.calero.lili.api.dtos.vtClientes.*;
import com.calero.lili.api.errors.exceptions.AlreadyExistsException;
import com.calero.lili.api.errors.exceptions.ReadOnlyException;
import com.calero.lili.api.repositories.VtClientesRepository;
import com.calero.lili.api.repositories.entities.VtClientesEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.text.MessageFormat;
import java.util.*;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class VtClientesServiceImpl {

    private final VtClientesRepository vtClientesRepository;

    public VtClienteCreationResponseDto create(VtClienteCreationRequestDto request) {
        String idData = "ABCD";

        VtClientesEntity vtClientesEntity = vtClientesRepository.findByIdDataAndNumeroIdentificacion(idData,request.getNumeroIdentificacion());
        if(vtClientesEntity!=null) {
            System.out.println(request.getNumeroIdentificacion());
            throw new AlreadyExistsException(MessageFormat.format("El cliente con número de identificación {0} ya existe", request.getNumeroIdentificacion()));
        }
        VtClientesEntity  vtClientesNew = new VtClientesEntity();
        vtClientesNew.setIdData(idData);
        vtClientesNew.setIdCliente(UUID.randomUUID());

        List<VtClientesEntity.Direccion> listaDireccionesEntity = new ArrayList<>();
        List<VtClienteCreationRequestDto.Direccion> listaDireccionesDto = request.getDirecciones();
        for (VtClienteCreationRequestDto.Direccion direccionDto : listaDireccionesDto ){
            VtClientesEntity.Direccion direccionEntity = new VtClientesEntity.Direccion();
            direccionEntity.setDireccion(direccionDto.getDireccion());
            direccionEntity.setTelefonos(direccionDto.getTelefonos());
            direccionEntity.setCiudad(direccionDto.getCiudad());
            direccionEntity.setEmail(direccionDto.getEmail());
            direccionEntity.setContacto(direccionDto.getContacto());
            listaDireccionesEntity.add(direccionEntity);
        }
        vtClientesNew.setDirecciones(listaDireccionesEntity);
        return toResponseDto(vtClientesRepository.save(toEntity(request, vtClientesNew)));
    }


    public VtClienteCreationResponseDto update(UUID id, VtClienteCreationRequestDto request) {
        String idData = "ABCD";

        VtClientesEntity entidad = vtClientesRepository.findByIdDataAndIdCliente(idData, id);
        if (entidad != null) {
            List<VtClientesEntity.Direccion> listaDireccionesEntity = new ArrayList<>();
            List<VtClienteCreationRequestDto.Direccion> listaDireccionesDto = request.getDirecciones();
            for (VtClienteCreationRequestDto.Direccion direccionDto : listaDireccionesDto ){
                VtClientesEntity.Direccion direccionEntity = new VtClientesEntity.Direccion();
                direccionEntity.setDireccion(direccionDto.getDireccion());
                direccionEntity.setTelefonos(direccionDto.getTelefonos());
                direccionEntity.setCiudad(direccionDto.getCiudad());
                direccionEntity.setEmail(direccionDto.getEmail());
                direccionEntity.setContacto(direccionDto.getContacto());
                listaDireccionesEntity.add(direccionEntity);
            }
            entidad.setDirecciones(listaDireccionesEntity);
            entidad = vtClientesRepository.save(toEntity(request, entidad));
        } else {
            throw new ReadOnlyException(format("Id {0} no existe", id));
        }
        return toResponseDto(entidad);
    }


    public VtClienteFindByIdDto findById(UUID id) {
        String idData = "ABCD";
        VtClientesEntity entidad = vtClientesRepository.findByIdDataAndIdCliente(idData, id);
        if (entidad == null) {
            throw new ReadOnlyException(MessageFormat.format("idCliente {0} no exists", id));
        }
        return toDto(entidad);
    }

    private VtClienteCreationResponseDto toResponseDto(VtClientesEntity entity) {
        VtClienteCreationResponseDto dto = new VtClienteCreationResponseDto();
        dto.setIdCliente(entity.getIdCliente());
        return dto;
    }

    private VtClienteFindByIdDto toDto(VtClientesEntity entity) {
        VtClienteFindByIdDto dto = new VtClienteFindByIdDto();
        dto.setIdCliente(entity.getIdCliente());
        dto.setCliente(entity.getCliente());
        dto.setTipoIdentificacion(entity.getTipoIdentificacion());
        dto.setNumeroIdentificacion(entity.getNumeroIdentificacion());
        dto.setWeb(entity.getWeb());
        List<VtClienteFindByIdDto.Direccion> listaDireccionesDto = new ArrayList<>();
        List<VtClientesEntity.Direccion> listaDireccionesEntity = entity.getDirecciones();
        for(VtClientesEntity.Direccion direccionEntity : listaDireccionesEntity){
            VtClienteFindByIdDto.Direccion dir = new VtClienteFindByIdDto.Direccion();
            dir.setDireccion(direccionEntity.getDireccion());
            dir.setTelefonos(direccionEntity.getTelefonos());
            dir.setContacto(direccionEntity.getContacto());
            dir.setCiudad(direccionEntity.getCiudad());
            dir.setEmail(direccionEntity.getEmail());
            listaDireccionesDto.add(dir);
        }
        dto.setDirecciones(listaDireccionesDto);
        dto.setTipoCliente("01");
        dto.setRelacionado(Boolean.FALSE);
        return dto;
    }

    private VtClientesEntity toEntity(VtClienteCreationRequestDto request, VtClientesEntity entidad) {
        entidad.setCliente(request.getCliente());
        entidad.setTipoIdentificacion(request.getTipoIdentificacion());
        entidad.setNumeroIdentificacion(request.getNumeroIdentificacion());
        entidad.setWeb(request.getWeb());
        entidad.setTipoCliente(request.getTipoCliente());
        entidad.setObservaciones(request.getObservaciones());
        return entidad;
    }
}