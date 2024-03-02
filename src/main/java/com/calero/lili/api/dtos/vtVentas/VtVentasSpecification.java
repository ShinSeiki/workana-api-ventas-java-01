package com.calero.lili.api.dtos.vtVentas;

import com.calero.lili.api.repositories.entities.VtVentaDetalleEntity;
import com.calero.lili.api.repositories.entities.VtVentaEntity;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Clase FacturaSpecification
 *
 * Utiliza el patrón de diseño "Specification Pattern" para encapsular las reglas
 * de filtrado de entidades de factura y sus detalles en objetos reutilizables.
 *
 * Patrones de Diseño Utilizados:
 * - Specification Pattern
 * - Strategy Pattern
 *
 * @version 1.0
 */
public class VtVentasSpecification {

    /**
     * Método buildFromFilter
     *
     * Construye una especificación compuesta para la búsqueda de entidades de factura.
     * Utiliza el patrón "Strategy Pattern" para aplicar múltiples estrategias de filtrado.
     *
     * @param filter El objeto FacturaFilter que contiene los criterios de búsqueda.
     * @return Specification<VtFacturaEntity> Especificación compuesta.
     */
    public static Specification<VtVentaEntity> buildFromFilter(VtVentasFilterSpecification filter) {

        Specification<VtVentaEntity> spec = Specification.where(null);

        Stream<Specification<VtVentaEntity>> strategies = Stream.of(
                Optional.ofNullable(filter.getIdData()).map(VtVentasSpecification::idDataEquals).orElse(null),
                Optional.ofNullable(filter.getIdEmpresa()).map(VtVentasSpecification::idEmpresaEquals).orElse(null),
                Optional.ofNullable(filter.getIdCliente()).map(VtVentasSpecification::idClienteEquals).orElse(null),
                Optional.ofNullable(filter.getNumeroIdentificacion()).map(VtVentasSpecification::numeroIdentificacionLike).orElse(null),
                Optional.ofNullable(filter.getFechaEmisionDesde()).map(fechaEmisionDesde ->
                                Optional.ofNullable(filter.getFechaEmisionHasta())
                                        .map(fechaEmisionHasta -> VtVentasSpecification.fechaEmisionBetween(fechaEmisionDesde, fechaEmisionHasta))
                                        .orElse(VtVentasSpecification.fechaEmisionEquals(fechaEmisionDesde)))
                        .orElse(null),
                Optional.ofNullable(filter.getSerie()).map(VtVentasSpecification::serieEquals).orElse(null),
                Optional.ofNullable(filter.getSecuencia()).map(VtVentasSpecification::secuenciaLike).orElse(null)

        ).filter(s -> s != null);

        spec = strategies.reduce(spec, Specification::and);

        return spec;
    }

    /**
     * Método buildFromFilterForDetails
     *
     * Construye una especificación compuesta para la búsqueda de entidades de detalles de factura.
     * Utiliza el patrón "Strategy Pattern" para aplicar múltiples estrategias de filtrado.
     *
     * @param filter El objeto FacturaFilter que contiene los criterios de búsqueda.
     * @return Specification<VtFacturaDetalleEntity> Especificación compuesta.
     */
    public static Specification<VtVentaDetalleEntity> buildFromFilterForDetails(VtVentasFilterSpecification filter) {
        Specification<VtVentaDetalleEntity> spec = Specification.where(null);

        // Añadiendo las nuevas estrategias para los detalles de factura
        Stream<Specification<VtVentaDetalleEntity>> detalleStrategies = Stream.of(
                Optional.ofNullable(filter.getCodigoItem()).map(VtVentasSpecification::codigoItemEquals).orElse(null),
                Optional.ofNullable(filter.getItem()).map(VtVentasSpecification::itemEquals).orElse(null),
                Optional.ofNullable(filter.getPrecioUnitario()).map(VtVentasSpecification::precioUnitarioEquals).orElse(null),
                Optional.ofNullable(filter.getCantidad()).map(VtVentasSpecification::cantidadEquals).orElse(null),
                Optional.ofNullable(filter.getDescuento()).map(VtVentasSpecification::descuentoEquals).orElse(null),
                Optional.ofNullable(filter.getIdItem()).map(VtVentasSpecification::idItemEquals).orElse(null)
        ).filter(s -> s != null);

        spec = detalleStrategies.reduce(spec, Specification::and);

        return spec;
    }

    public static Specification<VtVentaDetalleEntity> idItemEquals(UUID idItem) {
        return (root, query, cb) -> cb.equal(root.join("items").get("idItem"), idItem);
    }

    public static Specification<VtVentaEntity> idDataEquals(String idData) {
        return (root, query, cb) -> cb.equal(root.get("idData"), idData);
    }

    public static Specification<VtVentaEntity> idEmpresaEquals(String idEmpresa) {
        return (root, query, cb) -> cb.equal(root.get("idEmpresa"), idEmpresa);
    }

    public static Specification<VtVentaEntity> idClienteEquals(UUID idCliente) {
        return (root, query, cb) -> cb.equal(root.join("cliente").get("idCliente"), idCliente);
    }


    public static Specification<VtVentaEntity> numeroIdentificacionLike(String numeroIdentificacion) {
        return (root, query, cb) -> cb.like(root.join("cliente").get("numeroIdentificacion"), "%" + numeroIdentificacion + "%");
    }

    public static Specification<VtVentaEntity> fechaEmisionEquals(LocalDate fechaEmision) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("fechaEmision"), fechaEmision);
    }

    public static Specification<VtVentaEntity> fechaEmisionBetween(LocalDate fechaEmisionDesde, LocalDate fechaEmisionHasta) {
        return (root, query, cb) -> cb.between(root.get("fechaEmision"), fechaEmisionDesde, fechaEmisionHasta);
    }

    public static Specification<VtVentaEntity> serieEquals(String serie) {
        return (root, query, cb) -> cb.equal(root.get("serie"), serie);
    }

    public static Specification<VtVentaEntity> secuenciaLike(String secuencia) {
        return (root, query, cb) -> cb.like(root.get("secuencia"), "%" + secuencia + "%");
    }

    // Métodos nuevos para filtrar detalles de factura
    public static Specification<VtVentaDetalleEntity> codigoItemEquals(String codigoItem) {
        return (root, query, cb) -> cb.equal(root.join("vtFacturaDetalleEntity").get("codigoItem"), codigoItem);
    }

    public static Specification<VtVentaDetalleEntity> itemEquals(String item) {
        return (root, query, cb) -> cb.equal(root.join("vtFacturaDetalleEntity").get("item"), item);
    }

    public static Specification<VtVentaDetalleEntity> precioUnitarioEquals(BigDecimal precioUnitario) {
        return (root, query, cb) -> cb.equal(root.join("vtFacturaDetalleEntity").get("precioUnitario"), precioUnitario);
    }

    public static Specification<VtVentaDetalleEntity> cantidadEquals(BigDecimal cantidad) {
        return (root, query, cb) -> cb.equal(root.join("vtFacturaDetalleEntity").get("cantidad"), cantidad);
    }

    public static Specification<VtVentaDetalleEntity> descuentoEquals(BigDecimal descuento) {
        return (root, query, cb) -> cb.equal(root.join("vtFacturaDetalleEntity").get("descuento"), descuento);
    }

}
