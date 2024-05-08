package com.calero.lili.api.services;

import com.calero.lili.api.dtos.errors.ListCreationResponseDto;
import com.calero.lili.api.dtos.errors.DetallesErrores;
import com.calero.lili.api.dtos.geItems.*;
import com.calero.lili.api.errors.exceptions.AlreadyExistsException;
import com.calero.lili.api.errors.exceptions.ReadOnlyException;
import com.calero.lili.api.repositories.entities.*;
import com.calero.lili.api.repositories.projections.GeItemsProjection;
import com.calero.lili.api.repositories.GeItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class GeItemsServiceImpl {

    private final GeItemsRepository geItemsRepository;

    public GeItemCreationResponseDto create(String idData, String idEmpresa, GeItemCreationRequestDto request) {
        GeItemsEntity geItemsEntity  = geItemsRepository.findByIdDataAndIdEmpresaAndCodigoItem(idData, idEmpresa, request.getCodigoItem());
        if(geItemsEntity!=null) {
            throw new AlreadyExistsException(MessageFormat.format("El item con codigo {0} ya existe", request.getCodigoItem()));
        }
        GeItemsEntity entidad = new GeItemsEntity();
        entidad.setIdData(idData);
        entidad.setIdEmpresa(idEmpresa);
        entidad.setIdItem(UUID.randomUUID());
        return toDto(geItemsRepository.save(toEntity(request, entidad)));
    }

    public ListCreationResponseDto createListItems(String idData, String idEmpresa, GeItemListCreationRequestDto request) {
        ListCreationResponseDto response = new ListCreationResponseDto();
        List<DetallesErrores> detallesErrores = new ArrayList<>();
        List<GeItemCreationRequestDto> listaItems = request.getListaItems();

        // Lista de opciones válidas
        List<String> opcionesValidas = Arrays.asList("R", "C", "P");

        IntStream.range(0, listaItems.size())
                .forEach(index -> {
                    GeItemCreationRequestDto requestDto = listaItems.get(index);
                    try {
                        String valido ="";

                        if(requestDto.getCodigoItem() == null || requestDto.getCodigoItem().isEmpty()) {
                            DetallesErrores detalleError = new DetallesErrores();
                            detalleError.setIndex(index);
                            detalleError.setDescripcion("El codigo de item no existe");
                            detallesErrores.add(detalleError);
                            valido="N";
                        }

                        if(requestDto.getItem() == null || requestDto.getItem().isEmpty()) {
                            DetallesErrores detalleError = new DetallesErrores();
                            detalleError.setIndex(index);
                            detalleError.setDescripcion("El nombre del item no existe");
                            detallesErrores.add(detalleError);
                            valido="N";
                        }

                        if(requestDto.getCodigoIva()==null || requestDto.getCodigoIva().isEmpty()) {
                            DetallesErrores detalleError = new DetallesErrores();
                            detalleError.setIndex(index);
                            detalleError.setDescripcion("El codigo de IVA no existe");
                            detallesErrores.add(detalleError);
                            valido="N";
                        }

                        if (valido.equals("")) {
                            GeItemsEntity geItemsExist = geItemsRepository.findByIdDataAndIdEmpresaAndCodigoItem(idData, idEmpresa, requestDto.getCodigoItem());
                            if(geItemsExist!=null) {
                                // MODIFICAR
                                List <GeItemsEntity.Impuesto> listaImpuesto = new ArrayList<GeItemsEntity.Impuesto>();
                                GeItemsEntity.Impuesto impuesto = new GeItemsEntity.Impuesto();

                                if (requestDto.getCodigoIva().equals("0")){
                                    impuesto.setImpuesto("IV");
                                    impuesto.setCodigo("0");
                                    impuesto.setPorcentaje(String.valueOf(0));
                                    listaImpuesto.add(impuesto);
                                }
                                if (requestDto.getCodigoIva().equals("1")){
                                    impuesto.setImpuesto("IV");
                                    impuesto.setCodigo("1");
                                    impuesto.setPorcentaje(String.valueOf(15));
                                    listaImpuesto.add(impuesto);
                                }
                                geItemsExist.setImpuestos(listaImpuesto);

                                geItemsRepository.save(geItemsExist);
                            }
                            else {
                                // AGREGAR
                                GeItemsEntity  geItemsNew = new GeItemsEntity();
                                geItemsNew.setIdData(idData);
                                geItemsNew.setIdEmpresa(idEmpresa);
                                geItemsNew.setIdItem(UUID.randomUUID());
                                geItemsNew = toEntity(requestDto, geItemsNew);

                                List <GeItemsEntity.Impuesto> listaImpuesto = new ArrayList<GeItemsEntity.Impuesto>();
                                GeItemsEntity.Impuesto impuesto = new GeItemsEntity.Impuesto();

                                if (requestDto.getCodigoIva().equals("0")){
                                    impuesto.setImpuesto("IV");
                                    impuesto.setCodigo("0");
                                    impuesto.setPorcentaje(String.valueOf(0));
                                    listaImpuesto.add(impuesto);
                                }
                                if (requestDto.getCodigoIva().equals("1")){
                                    impuesto.setImpuesto("IV");
                                    impuesto.setCodigo("1");
                                    impuesto.setPorcentaje(String.valueOf(15));
                                    listaImpuesto.add(impuesto);
                                }

                                geItemsNew.setImpuestos(listaImpuesto);
                                geItemsRepository.save(geItemsNew);
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

    public GeItemCreationResponseDto update(String idData, String idEmpresa, UUID id, GeItemCreationRequestDto request) {
        GeItemsEntity entidad = geItemsRepository.findByIdDataAndIdEmpresaAndIdItem(idData, idEmpresa, id);
        if (entidad != null) {
            entidad = geItemsRepository.save(toEntity(request, entidad));
        } else {
            throw new ReadOnlyException(format("Id {0} no existe", id));
        }
        return toDto(entidad);
    }

    public void delete(String idData, String idEmpresa, UUID id) {
        geItemsRepository.deleteByIdDataAndEmpresaAndIdItem(idData, idEmpresa, id);
    }

    public GeItemCreationResponseDto findById(String idData, String idEmpresa, UUID id) {
        GeItemsEntity entidad = geItemsRepository.findByIdDataAndIdEmpresaAndIdItem(idData, idEmpresa, id);
        if (entidad == null) {
            throw new ReadOnlyException(format("Id {0} no existe", id));
        }
        return toDto(entidad);
    }

    public Page<GeItemReportDto> findAllPaginate(String idData, String idEmpresa, GeItemListFilterDto filters, Pageable pageable) {
        Page<GeItemsProjection>page = geItemsRepository.findAllPaginate(idData, idEmpresa, filters.getFilter(), pageable);
        return new PageImpl<>(
                page.getContent()
                        .stream()
                        .map(this::projectionToDtoReport)
                        .collect(Collectors.toList()),
                page.getPageable(),
                page.getTotalElements());
    }

    private GeItemReportDto projectionToDtoReport(GeItemsProjection projection) {
        GeItemReportDto dto = new GeItemReportDto();
        dto.setIdItem(projection.getIdItem());
        dto.setCodigoItem(projection.getCodigoItem());
        dto.setCodigoBarras(projection.getCodigoBarras());
        dto.setItem(projection.getItem());
        dto.setDetallesAdicionales(projection.getDetallesAdicionales());
        dto.setImpuestos(projection.getImpuestos());
        return dto;
    }

    private GeItemsEntity toEntity(GeItemCreationRequestDto request, GeItemsEntity entidad) {
        entidad.setCodigoItem(request.getCodigoItem());
        entidad.setCodigoBarras(request.getCodigoBarras());
        entidad.setItem(request.getItem());
        entidad.setDetallesAdicionales(request.getDetallesAdicionales());
        entidad.setImpuestos(request.getImpuestos());
        return entidad;
    }
    private GeItemCreationResponseDto toDto(GeItemsEntity entity) {
        GeItemCreationResponseDto dto = new GeItemCreationResponseDto();
        dto.setIdItem(entity.getIdItem());
        dto.setCodigoItem(entity.getCodigoItem());
        dto.setCodigoBarras(entity.getCodigoBarras());
        dto.setItem(entity.getItem());
        dto.setDetallesAdicionales(entity.getDetallesAdicionales());
        dto.setImpuestos(entity.getImpuestos());
        return dto;
    }
}
