package com.calero.lili.api.services;

import com.calero.lili.api.dtos.errors.ListCreationResponseDto;
import com.calero.lili.api.dtos.errors.DetallesErrores;
import com.calero.lili.api.dtos.vtClientes.*;
import com.calero.lili.api.errors.exceptions.AlreadyExistsException;
import com.calero.lili.api.errors.exceptions.ReadOnlyException;
import com.calero.lili.api.repositories.VtClientesRepository;
import com.calero.lili.api.repositories.entities.VtClientesEntity;
import com.calero.lili.api.repositories.projections.VtClientesProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class VtClientesServiceImpl {

//    private final ValidarIdentificacion validarIdentificacion;
    private final VtClientesRepository vtClientesRepository;

    public VtClienteCreationResponseDto create(String idData, VtClienteCreationRequestDto request) {
        VtClientesEntity vtClientesEntity = vtClientesRepository.findByIdDataAndNumeroIdentificacion(idData,request.getNumeroIdentificacion());
        if(vtClientesEntity!=null) {
            System.out.println(request.getNumeroIdentificacion());
            throw new AlreadyExistsException(MessageFormat.format("El cliente con número de identificación {0} ya existe", request.getNumeroIdentificacion()));
        }
        VtClientesEntity  vtClientesNew = new VtClientesEntity();
        vtClientesNew.setIdData(idData);
        vtClientesNew.setIdCliente(UUID.randomUUID());
        vtClientesNew.setDirecciones(request.getDirecciones());
        return toResponseDto(vtClientesRepository.save(toEntity(request, vtClientesNew)));
    }

    public ListCreationResponseDto createListCliente(String idData, VtClienteListCreationRequestDto request) {
        ListCreationResponseDto response = new ListCreationResponseDto();
        List<DetallesErrores> detallesErrores = new ArrayList<>();
        List<VtClienteCreationRequestDto> listaClientes = request.getListaClientes();

        // Lista de opciones válidas
        List<String> opcionesValidas = Arrays.asList("R", "C", "P");

        IntStream.range(0, listaClientes.size())
                .forEach(index -> {
                    VtClienteCreationRequestDto requestDto = listaClientes.get(index);
                    try {
                        String valido ="";

                        if(requestDto.getTipoIdentificacion().isEmpty()) {
                            DetallesErrores detalleError = new DetallesErrores();
                            detalleError.setIndex(index);
                            detalleError.setDescripcion("El tipo de identificacion no existe");
                            detallesErrores.add(detalleError);
                            valido="N";
                        }

                        if(requestDto.getNumeroIdentificacion().isEmpty()) {
                            DetallesErrores detalleError = new DetallesErrores();
                            detalleError.setIndex(index);
                            detalleError.setDescripcion("El numero de identificacion no existe");
                            detallesErrores.add(detalleError);
                            valido="N";
                        }

                        if(!opcionesValidas.contains(requestDto.getTipoIdentificacion())) {
                            DetallesErrores detalleError = new DetallesErrores();
                            detalleError.setIndex(index);
                            detalleError.setDescripcion("El tipo de identificacion es invalido: "+requestDto.getNumeroIdentificacion());
                            detallesErrores.add(detalleError);
                            valido="N";
                        }

                        if (requestDto.getTipoIdentificacion().equals("R") && !requestDto.getNumeroIdentificacion().isEmpty()) {
                            if ( requestDto.getNumeroIdentificacion().length() != 13) {
                                DetallesErrores detalleError = new DetallesErrores();
                                detalleError.setIndex(index);
                                detalleError.setDescripcion("El numero de RUC es invalido: " + requestDto.getNumeroIdentificacion());
                                detallesErrores.add(detalleError);
                                valido = "N";
                            } else {
                                if (!requestDto.getNumeroIdentificacion().endsWith("001")) {
                                    DetallesErrores detalleError = new DetallesErrores();
                                    detalleError.setIndex(index);
                                    detalleError.setDescripcion("El numero de RUC es invalido: " + requestDto.getNumeroIdentificacion());
                                    detallesErrores.add(detalleError);
                                    valido = "N";
                                }
                            }
                        }

                        if(requestDto.getDireccion().isEmpty()) {
                            DetallesErrores detalleError = new DetallesErrores();
                            detalleError.setIndex(index);
                            detalleError.setDescripcion("La direccion no existe");
                            detallesErrores.add(detalleError);
                            valido="N";
                        }

                        if(requestDto.getTipoIdentificacion().equals("C") && requestDto.getNumeroIdentificacion().length()!=10) {
                            DetallesErrores detalleError = new DetallesErrores();
                            detalleError.setIndex(index);
                            detalleError.setDescripcion("El numero de cedula es invalido: "+requestDto.getNumeroIdentificacion());
                            detallesErrores.add(detalleError);
                            valido="N";
                        }

                        if (valido.equals("")) {
                            VtClientesEntity vtClientesExist = vtClientesRepository.findByIdDataAndNumeroIdentificacion(idData,requestDto.getNumeroIdentificacion());
                            if(vtClientesExist!=null) {
                                // MODIFICAR

                                List<VtClientesEntity.Direccion> listaDir = new ArrayList<VtClientesEntity.Direccion>();
                                VtClientesEntity.Direccion dir = new VtClientesEntity.Direccion();
                                dir.setDireccion(requestDto.getDireccion());
                                dir.setTelefonos(requestDto.getTelefonos());
                                dir.setCiudad(requestDto.getCiudad());
                                dir.setEmail(requestDto.getEmails());
                                dir.setContacto(requestDto.getContacto());
                                listaDir.add(dir);
                                vtClientesExist.setDirecciones(listaDir);

                                vtClientesExist = toEntity(requestDto,vtClientesExist);
                                vtClientesRepository.save(vtClientesExist);
                            }
                            else {
                                // AGREGAR
                                VtClientesEntity  vtClientesNew = new VtClientesEntity();
                                vtClientesNew.setIdData(idData);
                                vtClientesNew.setIdCliente(UUID.randomUUID());

                                List<VtClientesEntity.Direccion> listaDir = new ArrayList<VtClientesEntity.Direccion>();
                                VtClientesEntity.Direccion dir = new VtClientesEntity.Direccion();
                                dir.setDireccion(requestDto.getDireccion());
                                dir.setTelefonos(requestDto.getTelefonos());
                                dir.setCiudad(requestDto.getCiudad());
                                dir.setEmail(requestDto.getEmails());
                                dir.setContacto(requestDto.getContacto());
                                listaDir.add(dir);
                                vtClientesNew.setDirecciones(listaDir);

                                vtClientesNew = toEntity(requestDto, vtClientesNew);
                                vtClientesRepository.save(vtClientesNew);
                            }

                        }

                    }catch (Exception e) {
                        // TODO: handle exception
                        DetallesErrores detalleError = new DetallesErrores();
                        detalleError.setIndex(index);
                        detalleError.setDescripcion("Se tiene el siguiente error:"+e.getMessage());
                        detallesErrores.add(detalleError);

                    }

                });
        response.setDetallesErrores(detallesErrores);
        if(detallesErrores.size()>0) {
            response.setRespuesta("Se encontraron errores");
        }
        else {
            response.setRespuesta("Exitoso");
        }
        return response;
    }

    public VtClienteCreationResponseDto update(String idData, UUID id, VtClienteCreationRequestDto request) {
        VtClientesEntity entidad = vtClientesRepository.findByIdDataAndIdCliente(idData, id);
        if (entidad != null) {
            entidad.setDirecciones(request.getDirecciones());
            entidad = vtClientesRepository.save(toEntity(request, entidad));
        } else {
            throw new ReadOnlyException(format("Id {0} no existe", id));
        }
        return toResponseDto(entidad);
    }

    public void delete(String idData, UUID id) {
        vtClientesRepository.deleteByIdDataAndIdCAndCliente(idData, id);
    }

    public VtClienteDto findById(String idData, UUID id) {
        VtClientesEntity entidad = vtClientesRepository.findByIdDataAndIdCliente(idData, id);
        if (entidad == null) {
            throw new ReadOnlyException(format("Id {0} no existe", id));
        }
        return toDto(entidad);
    }

    public Page<VtClienteReportDto> findAllPaginate(String idData, VtClienteFilterDto filters, Pageable pageable) {
        Page<VtClientesProjection> page = vtClientesRepository.findAllPaginate(idData, filters.getFilter(), pageable);
        return new PageImpl<>(
                page.getContent()
                        .stream()
                        .map(this::projectionToDtoReport)
                        .collect(Collectors.toList()),
                page.getPageable(),
                page.getTotalElements());
    }

    private VtClienteReportDto projectionToDtoReport(VtClientesProjection projection) {
        VtClienteReportDto dto = new VtClienteReportDto();
        dto.setIdCliente(projection.getIdCliente());
        dto.setCliente(projection.getCliente());
        dto.setTipoIdentificacion(projection.getTipoIdentificacion());
        dto.setNumeroIdentificacion(projection.getNumeroIdentificacion());
        dto.setWeb(projection.getWeb());
        dto.setObservaciones(projection.getObservaciones());
        dto.setDirecciones(projection.getDirecciones());

        return dto;
    }

    private VtClienteCreationResponseDto toResponseDto(VtClientesEntity entity) {
        VtClienteCreationResponseDto dto = new VtClienteCreationResponseDto();
        dto.setIdCliente(entity.getIdCliente());
        return dto;
    }

    private VtClienteDto toDto(VtClientesEntity entity) {
        VtClienteDto dto = new VtClienteDto();
        dto.setIdCliente(entity.getIdCliente());
        dto.setCliente(entity.getCliente());
        dto.setTipoIdentificacion(entity.getTipoIdentificacion());
        dto.setNumeroIdentificacion(entity.getNumeroIdentificacion());
        dto.setWeb(entity.getWeb());
        dto.setDirecciones(entity.getDirecciones());
        dto.setTipoCliente("01");
        dto.setRelacionado(Boolean.FALSE);
        return dto;
    }

//    private VtClientesDireccionesEntity toDireccionesEntity(VtClienteDirecciones direccion, VtClientesEntity cliente) {
//        VtClientesDireccionesEntity direccionesEntity = new VtClientesDireccionesEntity();
//        direccionesEntity.setCiudad(direccion.getCiudad());
//        direccionesEntity.setContacto(direccion.getContacto());
//        direccionesEntity.setDireccion(direccion.getDireccion());
//        direccionesEntity.setTelefonos(direccion.getTelefonos());
//        direccionesEntity.setEmail(direccion.getEmail());
//        direccionesEntity.setIdClientesDirecciones(UUID.randomUUID());
//        direccionesEntity.setDirecciones(cliente);// a quien pertenece la direccion
//        // SOLO SE PERMITE UNA DIRECCION POR DEFECTO LA PONGO COMO PREDETERMINADA
//        direccionesEntity.setPredeterminada(true);
//        direccionesEntity = vtClientesDireccionesRepository.save(direccionesEntity);
//        return direccionesEntity;
//    }

    private VtClientesEntity toEntity(VtClienteCreationRequestDto request, VtClientesEntity entidad) {
        entidad.setCliente(request.getCliente());
        entidad.setTipoIdentificacion(request.getTipoIdentificacion());
        entidad.setNumeroIdentificacion(request.getNumeroIdentificacion());
        entidad.setWeb(request.getWeb());
        entidad.setTipoCliente(request.getTipoCliente());
        entidad.setObservaciones(request.getObservaciones());
        return entidad;
    }

//    public void updateAddress(List<VtClienteDirecciones> direcciones,VtClientesEntity VtCliente) {
//        List<VtClientesDireccionesEntity> lstDirecciones = new ArrayList();
//        vtClientesDireccionesRepository.deleteAll(VtCliente.getVtClientesDireccionesEntities());
//        direcciones.stream().forEach((c) -> {
//            lstDirecciones.add(toDireccionesEntity(c,VtCliente));
//        });
//        VtCliente.setVtClientesDireccionesEntities(lstDirecciones);
//        vtClientesRepository.save(VtCliente);
//    }

//    public void saveAddressNewCliente(List<VtClienteDirecciones> direccionesDto,VtClientesEntity VtCliente) {
//        List<VtClientesDireccionesEntity> lstDirecciones = new ArrayList<>();
//
//        direccionesDto.stream().forEach((c) -> {
//            lstDirecciones.add(toDireccionesEntity(c,VtCliente));
//        });
//        VtCliente.setVtClientesDireccionesEntities(lstDirecciones);
//        vtClientesRepository.save(VtCliente);
//    }

}