package com.calero.lili.api.services;

import com.calero.lili.api.dtos.vtVentas.VtVentaReportDto;
import com.calero.lili.api.repositories.projections.VtVentasProjection;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface VtVentasMapper {
    @Mapping(target = "id", source = "idSucursal")
    @Mapping(target = "id", source = "idSucursal")
    @Mapping(target = "id", source = "idSucursal")
    @Mapping(target = "id", source = "idSucursal")
    @Mapping(target = "id", source = "idSucursal")
    VtVentaReportDto toDto(VtVentasProjection vtVentasProjection);

    @InheritInverseConfiguration
    VtVentasProjection toEntity(VtVentaReportDto vtVentaReportDto);
}
