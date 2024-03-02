package com.calero.lili.api.controllers;

import com.calero.lili.api.dtos.vtVentas.VtVentasFilterSpecification;
import com.calero.lili.api.repositories.entities.VtVentaDetalleEntity;
import com.calero.lili.api.repositories.entities.VtVentaEntity;
import com.calero.lili.api.dtos.vtVentas.VtVentaCreationRequestDto;
import com.calero.lili.api.dtos.vtVentas.VtVentaCreationResponseDto;
import com.calero.lili.api.services.VtVentasServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "api/v1.0/ventas")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*")

public class VtVentasController {

    private final VtVentasServiceImpl vtVentasService;
    private static final Logger logger = LoggerFactory.getLogger(VtVentasController.class);

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public VtVentaCreationResponseDto create(
            @RequestBody VtVentaCreationRequestDto request) {
        return vtVentasService.create(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VtVentaCreationResponseDto update(
            @PathVariable("id") UUID id,
            @RequestBody VtVentaCreationRequestDto request) {
        return vtVentasService.update(id, request);
    }


    //////////////////////////////////


    /**
     * Busca facturas según diferentes criterios.
     *
     * @param idData Identificador de la data.
     * @param idEmpresa Identificador de la empresa.
     * @param idCliente Identificador del cliente (opcional).
     * @param numeroIdentificacion Número de identificación (opcional).
     * @param fechaEmisionDesde Fecha de inicio para el rango de emisión de facturas.
     * @param fechaEmisionHasta Fecha de fin para el rango de emisión de facturas (opcional).
     * @param serie Serie de la factura (opcional).
     * @param secuencia Secuencia de la factura (opcional).
     * @return Respuesta HTTP que incluye un mapa con las facturas encontradas o un mensaje de error.
     */

    // FECHA ENVIAR FORMATO 2000-01-02
    @GetMapping("/buscar")
    public ResponseEntity<Map<String, Object>> buscarFacturas(
            @RequestParam(required = true) String idData,
            @RequestParam(required = true) String idEmpresa,
            @RequestParam(required = false) UUID idCliente,
            @RequestParam(required = false) String numeroIdentificacion,
            @RequestParam(name = "fechaEmisionDesde", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fechaEmisionDesde,
            @RequestParam(name = "fechaEmisionHasta", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fechaEmisionHasta,
            @RequestParam(required = false) String serie,
            @RequestParam(required = false) String secuencia
    ) {

        if ((fechaEmisionDesde == null || fechaEmisionHasta == null) && (serie == null || secuencia == null)) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Debe especificar el rango de fechas de emisión o serie y secuencia."), HttpStatus.BAD_REQUEST);
        }

        VtVentasFilterSpecification filter = new VtVentasFilterSpecification.Builder()
                .idData(idData)
                .idEmpresa(idEmpresa)
                .idCliente(idCliente)
                .numeroIdentificacion(numeroIdentificacion)
                .fechaEmisionDesde(fechaEmisionDesde)
                .fechaEmisionHasta(fechaEmisionHasta)
                .serie(serie)
                .secuencia(secuencia)
                .build();

        Optional<List<VtVentaEntity>> optionalFacturas = vtVentasService.findFacturasWithSpecification(filter);

        if (optionalFacturas.isPresent()) {
            List<VtVentaEntity> facturas = optionalFacturas.get();
            if (facturas.isEmpty()) {
                return new ResponseEntity<>(Collections.singletonMap("error", "No se encontraron facturas con los filtros proporcionados"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(Collections.singletonMap("facturas", facturas), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("error", "Ocurrió un error al buscar las facturas"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Obtiene los detalles de las facturas según los parámetros especificados.
     *
     * @param idData Identificador de la data. Es obligatorio para la búsqueda.
     * @param idEmpresa Identificador de la empresa. Es obligatorio para la búsqueda.
     * @param idCliente Identificador del cliente. Es un parámetro opcional.
     * @param numeroIdentificacion Número de identificación del cliente. Es un parámetro opcional.
     * @param fechaEmisionDesde Fecha desde la que se quieren obtener facturas. Es obligatorio.
     * @param fechaEmisionHasta Fecha hasta la que se quieren obtener facturas. Es un parámetro opcional.
     * @param serie Serie de la factura. Es un parámetro opcional.
     * @param secuencia Secuencia de la factura. Es un parámetro opcional.
     * @param idItem Identificador del ítem en la factura. Es un parámetro opcional.
     * @return Respuesta con los detalles de las facturas o un mensaje de error.
     */
    @GetMapping("/detalles")
    public ResponseEntity<Map<String, Object>> obtenerDetallesFactura(
            @RequestParam(required = true) String idData,
            @RequestParam(required = true) String idEmpresa,
            @RequestParam(required = false) UUID idCliente,
            @RequestParam(required = false) String numeroIdentificacion,
            @RequestParam(name = "fechaEmisionDesde", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fechaEmisionDesde,
            @RequestParam(name = "fechaEmisionHasta", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fechaEmisionHasta,
            @RequestParam(required = false) String serie,
            @RequestParam(required = false) String secuencia,
            @RequestParam(required = false) UUID idItem
    ) {


        if ((fechaEmisionDesde == null || fechaEmisionHasta == null) && (serie == null || secuencia == null)) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Debe especificar el rango de fechas de emisión o serie y secuencia."), HttpStatus.BAD_REQUEST);
        }

        VtVentasFilterSpecification filter = new VtVentasFilterSpecification.Builder()
                .idData(idData)
                .idEmpresa(idEmpresa)
                .idCliente(idCliente)
                .numeroIdentificacion(numeroIdentificacion)
                .fechaEmisionDesde(fechaEmisionDesde)
                .fechaEmisionHasta(fechaEmisionHasta)
                .serie(serie)
                .secuencia(secuencia)
                .idItem(idItem) // Parámetro adicional
                .build();

        Optional<List<VtVentaDetalleEntity>> facturas = vtVentasService.findFacturaDetailsWithSpecification(filter);
        Optional<List<VtVentaEntity>> FacturasDetalle = vtVentasService.findFacturasWithSpecification(filter);

        Map<String, Object> response = new HashMap<>();
        response.put("facturas", facturas);
        response.put("facturaDetalles", FacturasDetalle);

        if (facturas.isPresent() && FacturasDetalle.isPresent()) {
            List<VtVentaDetalleEntity> detalles = facturas.get();
            if (detalles.isEmpty()) {
                return new ResponseEntity<>(Collections.singletonMap("error", "No se encontraron los detalles de las facturas con los filtros proporcionados"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("error", "Ocurrió un error al buscar el detalle de las facturas"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene los totales de las facturas según los parámetros especificados.
     *
     * @param idData Identificador de la data. Es obligatorio para la búsqueda.
     * @param idEmpresa Identificador de la empresa. Es obligatorio para la búsqueda.
     * @param idCliente Identificador del cliente. Es un parámetro opcional.
     * @param numeroIdentificacion Número de identificación del cliente. Es un parámetro opcional.
     * @param fechaEmisionDesde Fecha desde la que se quieren obtener facturas. Es obligatorio.
     * @param fechaEmisionHasta Fecha hasta la que se quieren obtener facturas. Es un parámetro opcional.
     * @param serie Serie de la factura. Es un parámetro opcional.
     * @param secuencia Secuencia de la factura. Es un parámetro opcional.
     * @return Respuesta con los totales de las facturas o un mensaje de error.
     */
    @GetMapping("/totales")
    public ResponseEntity<Map<String, Object>> obtenerTotalesFacturas(
            @RequestParam(required = true) String idData,
            @RequestParam(required = true) String idEmpresa,
            @RequestParam(required = false) UUID idCliente,
            @RequestParam(required = false) String numeroIdentificacion,
            @RequestParam(name = "fechaEmisionDesde", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fechaEmisionDesde,
            @RequestParam(name = "fechaEmisionHasta", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fechaEmisionHasta,
            @RequestParam(required = false) String serie,
            @RequestParam(required = false) String secuencia
    ) {

        if ((fechaEmisionDesde == null || fechaEmisionHasta == null) && (serie == null || secuencia == null)) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Debe especificar el rango de fechas de emisión o serie y secuencia."), HttpStatus.BAD_REQUEST);
        }


        VtVentasFilterSpecification filter = new VtVentasFilterSpecification.Builder()
                .idData(idData)
                .idEmpresa(idEmpresa)
                .idCliente(idCliente)
                .numeroIdentificacion(numeroIdentificacion)
                .fechaEmisionDesde(fechaEmisionDesde)
                .fechaEmisionHasta(fechaEmisionHasta)
                .serie(serie)
                .secuencia(secuencia)
                .build();

        Optional<Map<String, BigDecimal>> totales = vtVentasService.calculateInvoiceTotalsWithSpecification(filter);
        Optional<List<VtVentaEntity>> Facturas = vtVentasService.findFacturasWithSpecification(filter);

        Map<String, Object> response = new HashMap<>();
        response.put("facturas", Facturas);
        response.put("totales", totales);

        if (totales.isPresent() && Facturas.isPresent()) {
            List<VtVentaEntity> facturas = Facturas.get();
            if (facturas.isEmpty()) {
                return new ResponseEntity<>(Collections.singletonMap("error", "No se encontraron facturas con los filtros proporcionados"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("error", "Ocurrió un error al buscar los totales de las facturas"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Obtiene los totales detallados de las facturas según los parámetros especificados.
     *
     * @param idData Identificador de la data. Es obligatorio para la búsqueda.
     * @param idEmpresa Identificador de la empresa. Es obligatorio para la búsqueda.
     * @param idCliente Identificador del cliente. Es un parámetro opcional.
     * @param numeroIdentificacion Número de identificación del cliente. Es un parámetro opcional.
     * @param fechaEmisionDesde Fecha desde la que se quieren obtener facturas. Es obligatorio.
     * @param fechaEmisionHasta Fecha hasta la que se quieren obtener facturas. Es un parámetro opcional.
     * @param serie Serie de la factura. Es un parámetro opcional.
     * @param secuencia Secuencia de la factura. Es un parámetro opcional.
     * @param idItem Identificador del ítem en la factura. Es un parámetro opcional.
     * @return Respuesta con los totales detallados de las facturas o un mensaje de error.
     */
    @GetMapping("/totales-detalles")
    public ResponseEntity<Map<String, Object>> obtenerTotalesFacturasDetalles(
            @RequestParam String idData,
            @RequestParam String idEmpresa,
            @RequestParam(required = false) UUID idCliente,
            @RequestParam(required = false) String numeroIdentificacion,
            @RequestParam(name = "fechaEmisionDesde", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fechaEmisionDesde,
            @RequestParam(name = "fechaEmisionHasta", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fechaEmisionHasta,
            @RequestParam(required = false) String serie,
            @RequestParam(required = false) String secuencia,
            @RequestParam(required = false) UUID idItem
    ) {

        if ((fechaEmisionDesde == null || fechaEmisionHasta == null) && (serie == null || secuencia == null)) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Debe especificar el rango de fechas de emisión o serie y secuencia."), HttpStatus.BAD_REQUEST);
        }


        VtVentasFilterSpecification filter = new VtVentasFilterSpecification.Builder()
                .idData(idData)
                .idEmpresa(idEmpresa)
                .idCliente(idCliente)
                .numeroIdentificacion(numeroIdentificacion)
                .fechaEmisionDesde(fechaEmisionDesde)
                .fechaEmisionHasta(fechaEmisionHasta)
                .serie(serie)
                .secuencia(secuencia)
                .idItem(idItem)
                .build();

        Optional<Map<String, BigDecimal>> totalesDetalles = vtVentasService.calculateInvoiceDetailsTotalsWithSpecification(filter);
        Optional<List<VtVentaEntity>> Facturas = vtVentasService.findFacturasWithSpecification(filter);
        Optional<List<VtVentaDetalleEntity>> FacturasDetalle = vtVentasService.findFacturaDetailsWithSpecification(filter);

        Map<String, Object> response = new HashMap<>();
        response.put("facturas", Facturas);
        response.put("facturaDetalles", FacturasDetalle);
        response.put("totalesDetalles", totalesDetalles);

        if (totalesDetalles.isPresent() && Facturas.isPresent() && FacturasDetalle.isPresent()) {
            List<VtVentaDetalleEntity> detalles = FacturasDetalle.get();
            if (detalles.isEmpty()) {
                return new ResponseEntity<>(Collections.singletonMap("error", "No se encontraron los detalles de las facturas con los filtros proporcionados"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("error", "Ocurrió un error al buscar los totales de los detalles de las facturas"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
