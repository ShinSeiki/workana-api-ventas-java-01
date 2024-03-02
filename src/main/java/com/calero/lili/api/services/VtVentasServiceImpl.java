package com.calero.lili.api.services;

import com.calero.lili.api.dtos.vtVentas.*;
import com.calero.lili.api.errors.exceptions.AlreadyExistsException;
import com.calero.lili.api.errors.exceptions.NotFoundException;
import com.calero.lili.api.repositories.VtClientesRepository;
import com.calero.lili.api.repositories.entities.*;
import com.calero.lili.api.repositories.projections.VtVentaDetalleProjection;
import com.calero.lili.api.repositories.projections.VtVentasProjection;
import com.calero.lili.api.utils.DateUtils;
import com.calero.lili.api.repositories.VtVentaDetalleRepository;
import com.calero.lili.api.repositories.VtVentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;


import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class VtVentasServiceImpl implements VtVentasService {

    private final VtVentaRepository vtFacturaRepository;
    private final VtVentaDetalleRepository vtFacturaDetalleRepository;
    private final VtClientesRepository vtClientesRepository;

    private static final Logger logger = LoggerFactory.getLogger(VtVentasServiceImpl.class);

    @Override
    @Transactional
    public VtVentaCreationResponseDto create(VtVentaCreationRequestDto request) {

        Optional<VtVentasProjection> existingFactura = vtFacturaRepository.findFirst(request.getSecuencia());
        if (existingFactura.isPresent()) {
            throw new AlreadyExistsException(format("La factura ya existe Tipo: {0} Serie: {1} Secuencia: {2}", request.getSecuencia()));
        }

        VtClientesEntity cliente = vtClientesRepository
                .findById(request.getIdCliente())
                .orElseThrow(() -> new NotFoundException(format("cliente con UUID {0} no existe", request.getIdCliente())));

        VtVentaEntity vtFacturaEntity = vtFacturaRepository.save(toEntity(request, cliente));

        List<VtVentaDetalleEntity> vtVentaDetalleEntity = request
                .getDetalleItems()
                .stream()
                .map(detail -> toDetalleEntity(vtFacturaEntity, detail))
                .collect(Collectors.toList());

        vtFacturaDetalleRepository.saveAll(vtVentaDetalleEntity);

        return toCreationResponse(vtFacturaEntity);
    }

    @Transactional
    public VtVentaCreationResponseDto update(UUID id, VtVentaCreationRequestDto request) {
        VtVentaEntity vtFacturaEntity = vtFacturaRepository.findById(id).orElseThrow(()-> new NotFoundException(format("El registro con id {0} no exists", id )));
        VtVentaEntity updatedInvoice = vtFacturaRepository.save(vtFacturaEntity);

        vtFacturaDetalleRepository.deleteAllByIdFactura(id);

        List<VtVentaDetalleEntity> vtVentaDetalleEntity = request
                .getDetalleItems()
                .stream()
                .map(detail -> toDetalleEntity(vtFacturaEntity, detail))
                .collect(Collectors.toList());

        vtFacturaDetalleRepository.saveAll(vtVentaDetalleEntity);

        return toCreationResponse(vtFacturaEntity);
    }

    private VtVentaEntity toEntity(VtVentaCreationRequestDto request, VtClientesEntity cliente) {
        VtVentaEntity vtFacturaEntity = new VtVentaEntity();
        vtFacturaEntity.setIdVenta(UUID.randomUUID());
        vtFacturaEntity.setFechaEmision(DateUtils.toLocalDate(request.getFechaEmision()));
        vtFacturaEntity.setSecuencia(request.getSecuencia());
        vtFacturaEntity.setTotalDescuento(getDescuento(request.getDetalleItems()));
        vtFacturaEntity.setCliente(cliente);
        return vtFacturaEntity;
    }

    private VtVentaDetalleEntity toDetalleEntity(VtVentaEntity vtFacturaEntity, VtVentaCreationRequestDetailDto vtFacturaCreationDetailDto) {
        VtVentaDetalleEntity vtVentaDetalleEntity = new VtVentaDetalleEntity();

        vtVentaDetalleEntity.setVenta(vtFacturaEntity);

        vtVentaDetalleEntity.setIdVentaDetalle(UUID.randomUUID());

            GeItemsEntity geItemsEntity = new GeItemsEntity();
            geItemsEntity.setIdItem(vtFacturaCreationDetailDto.getIdItem());

        vtVentaDetalleEntity.setItems(geItemsEntity);
        vtVentaDetalleEntity.setCodigoItem(vtFacturaCreationDetailDto.getCodigoItem());
        vtVentaDetalleEntity.setItem(vtFacturaCreationDetailDto.getItem());
        vtVentaDetalleEntity.setPrecioUnitario(vtFacturaCreationDetailDto.getPrecioUnitario());
        vtVentaDetalleEntity.setCantidad(vtFacturaCreationDetailDto.getCantidad());
        vtVentaDetalleEntity.setDescuento(vtFacturaCreationDetailDto.getDescuento());

        return vtVentaDetalleEntity;
    }

    private VtVentaCreationResponseDto toCreationResponse(VtVentaEntity vtFacturaEntity) {
        VtVentaCreationResponseDto response = new VtVentaCreationResponseDto();
        response.setIdFactura(vtFacturaEntity.getIdVenta());
        response.setDescuento(vtFacturaEntity.getTotalDescuento());
        return response;
    }


    private BigDecimal getDescuento(List<VtVentaCreationRequestDetailDto> vtFacturaCreationDetailDto) {
        return vtFacturaCreationDetailDto
                .stream()
                .map(VtVentaCreationRequestDetailDto::getDescuento)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Busca facturas según un conjunto de criterios de filtrado.
     *
     * Utiliza el patrón "Specification" para construir la consulta.
     *
     * @param filter Criterios de filtrado.
     * @return Optional<List<VtFacturaEntity>> Lista de facturas encontradas.
     */
    public Optional<List<VtVentaEntity>> findFacturasWithSpecification(VtVentasFilterSpecification filter) {
        try {
            logger.info("Iniciando la búsqueda de facturas con los siguientes filtros: {}", filter);
            Specification<VtVentaEntity> spec = VtVentasSpecification.buildFromFilter(filter);
            List<VtVentaEntity> facturas = vtFacturaRepository.findAll(spec);
            if (facturas.isEmpty()) {
                logger.warn("No se encontraron facturas con los filtros proporcionados.");
            } else {
                logger.info("Se encontraron {} facturas con los filtros proporcionados.", facturas.size());
            }
            return Optional.of(facturas);
        } catch (Exception e) {
            logger.error("Error durante la búsqueda de facturas: ", e);
            return Optional.empty();
        }
    }


    /**
     * Busca detalles de facturas según un conjunto de criterios de filtrado.
     *
     * Utiliza el patrón "Specification" para construir la consulta.
     *
     * @param filter Criterios de filtrado.
     * @return Optional<List<VtFacturaDetalleEntity>> Lista de detalles de facturas encontradas.
     */
    public Optional<List<VtVentaDetalleEntity>> findFacturaDetailsWithSpecification(VtVentasFilterSpecification filter) {
        try {
            logger.info("Iniciando la búsqueda de detalles de factura con los siguientes filtros: {}", filter);
            Specification<VtVentaDetalleEntity> spec = VtVentasSpecification.buildFromFilterForDetails(filter);
            List<VtVentaDetalleEntity> detalles = vtFacturaDetalleRepository.findAll(spec);
            if (detalles.isEmpty()) {
                logger.warn("No se encontraron detalles de factura con los filtros proporcionados.");
            } else {
                logger.info("Se encontraron {} detalles de factura con los filtros proporcionados.", detalles.size());
            }
            return Optional.ofNullable(detalles);
        } catch (Exception e) {
            logger.error("Error durante la búsqueda de detalles de factura: ", e);
            return Optional.empty();
        }
    }

    /**
     * Calcula los totales de facturas según un conjunto de criterios de filtrado.
     *
     * Utiliza el patrón "Specification" para construir la consulta.
     *
     * @param filter Criterios de filtrado.
     * @return Optional<Map<String, BigDecimal>> Mapa con los totales calculados.
     */
    public Optional<Map<String, BigDecimal>> calculateInvoiceTotalsWithSpecification(VtVentasFilterSpecification filter) {
        try {
            logger.info("Calculando totales de facturas con los siguientes filtros: {}", filter);
            Specification<VtVentaEntity> spec = VtVentasSpecification.buildFromFilter(filter);
            List<VtVentaEntity> facturas = vtFacturaRepository.findAll(spec);

            BigDecimal totalDescuento = facturas.stream().map(VtVentaEntity::getTotalDescuento).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal totalSubtotal = facturas.stream().map(VtVentaEntity::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);

            Map<String, BigDecimal> totales = new HashMap<>();
            totales.put("totalDescuento", totalDescuento);
            totales.put("totalSubtotal", totalSubtotal);

            return Optional.of(totales);
        } catch (Exception e) {
            logger.error("Error durante el cálculo de totales de facturas: ", e);
            return Optional.empty();
        }
    }

    /**
     * Calcula los totales de los detalles de facturas según un conjunto de criterios de filtrado.
     *
     * Utiliza el patrón "Specification" para construir la consulta.
     *
     * @param filter Criterios de filtrado.
     * @return Optional<Map<String, BigDecimal>> Mapa con los totales calculados.
     */
    public Optional<Map<String, BigDecimal>> calculateInvoiceDetailsTotalsWithSpecification(VtVentasFilterSpecification filter) {
        try {
            logger.info("Calculando totales de detalles de factura con los siguientes filtros: {}", filter);
            Specification<VtVentaDetalleEntity> spec = VtVentasSpecification.buildFromFilterForDetails(filter);
            List<VtVentaDetalleEntity> detalles = vtFacturaDetalleRepository.findAll(spec);

            BigDecimal totalDescuento = detalles.stream().map(VtVentaDetalleEntity::getDescuento).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal totalSubtotal = detalles.stream().map(detail -> detail.getPrecioUnitario().multiply(detail.getCantidad()).subtract(detail.getDescuento())).reduce(BigDecimal.ZERO, BigDecimal::add);

            Map<String, BigDecimal> totales = new HashMap<>();
            totales.put("totalDescuento", totalDescuento);
            totales.put("totalSubtotal", totalSubtotal);

            return Optional.of(totales);
        } catch (Exception e) {
            logger.error("Error durante el cálculo de totales de detalles de factura: ", e);
            return Optional.empty();
        }
    }

}
