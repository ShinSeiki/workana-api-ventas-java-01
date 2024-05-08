package com.calero.lili.api.controllers;

import com.calero.lili.api.annotations.VtVentasListFilter;
import com.calero.lili.api.dtos.vtVentas.VtVentaListFilterDto;
import com.calero.lili.api.dtos.vtVentas.VtVentaReportDto;
import com.calero.lili.api.specification.VtVentasFilterSpecification;
import com.calero.lili.api.repositories.entities.VtVentaDetalleEntity;
import com.calero.lili.api.repositories.entities.VtVentaEntity;
import com.calero.lili.api.dtos.vtVentas.VtVentaCreationRequestDto;
import com.calero.lili.api.dtos.vtVentas.VtVentaCreationResponseDto;
import com.calero.lili.api.dtos.vtVentas.VtVentaDetalleSummaryDto;
import com.calero.lili.api.dtos.vtVentas.VtVentaResponseFindByIdDto;
import com.calero.lili.api.dtos.vtVentas.VtVentaDetalleListFilterDto;
import com.calero.lili.api.services.VtVentasServiceImpl;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;


@Slf4j
@RestController
@RequestMapping(value = "api/v1.0/ventas")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*")

public class VtVentasController {

    private final VtVentasServiceImpl vtVentasService;
    private static final Logger logger = LoggerFactory.getLogger(VtVentasController.class);

    @PostMapping("{idData}/{idEmpresa}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public VtVentaCreationResponseDto create(
            @PathVariable("idData") String idData,
            @PathVariable("idEmpresa") String idEmpresa,
            @RequestBody VtVentaCreationRequestDto request) {
        return vtVentasService.create(idData, idEmpresa, request);
    }

    @PutMapping("{idData}/{idEmpresa}/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VtVentaCreationResponseDto update(
            @PathVariable("idData") String idData,
            @PathVariable("idEmpresa") String idEmpresa,
            @PathVariable("id") UUID id,
            @RequestBody VtVentaCreationRequestDto request) {
        return vtVentasService.update(idData, idEmpresa, id, request);
    }

    @DeleteMapping("{idData}/{idEmpresa}/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("idData") String idData,
            @PathVariable("idEmpresa") String idEmpresa,
            @PathVariable("id") UUID id) {
        vtVentasService.delete(idData, idEmpresa, id);
    }

    @GetMapping("{idData}/{idEmpresa}/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public VtVentaResponseFindByIdDto findById(
            @PathVariable("idData") String idData,
            @PathVariable("idEmpresa") String idEmpresa,
            @PathVariable("id") UUID id) {
        return vtVentasService.findByUuid(idData, idEmpresa,id);
    }

    @GetMapping("facturas/{idData}/{idEmpresa}")
    @ResponseStatus(code = HttpStatus.OK)
    public Page<VtVentaReportDto> findAllVentasPaginate(@PathVariable("idData") String idData,
                                          @PathVariable("idEmpresa") String idEmpresa,
                                          VtVentaListFilterDto filters,
                                          Pageable pageable) {
        System.out.println("iddata:"+idData);
        System.out.println("idEmpresa"+idEmpresa);
        log.info("Filters = {}", filters);
        //log.info(idData);
        return vtVentasService.findAllPaginate(idData, idEmpresa, filters, pageable);
    }

    @VtVentasListFilter // revisar
    @GetMapping("{idData}/{idEmpresa}/detalles")
    @ResponseStatus(code = HttpStatus.OK)
    public Page<VtVentaDetalleSummaryDto> findAllPaginateDetails(
            VtVentaDetalleListFilterDto filters,
            Pageable pageable) {
        log.info("Filters = {}", filters);
        return vtVentasService.findAllPaginateDetails(filters, pageable);
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

    // FECHA ENVIAR FORMATO 01/01/2000
    @GetMapping("/buscar")
    public ResponseEntity<Map<String, Object>> buscarFacturas(
            @RequestParam(required = true) String idData,
            @RequestParam(required = true) String idEmpresa,
            @RequestParam(required = false) UUID idCliente,
            @RequestParam(required = false) String numeroIdentificacion,
            @RequestParam(name = "fechaEmisionDesde", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fechaEmisionDesde,
            @RequestParam(name = "fechaEmisionHasta", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fechaEmisionHasta,
            @RequestParam(required = false) String serie,
            @RequestParam(required = false) String secuencia,
            Pageable pageable
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

        Optional<List<VtVentaEntity>> optionalFacturas = vtVentasService.findFacturasWithSpecification(filter, PageRequest.of(0, Integer.MAX_VALUE));

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
            @RequestParam(required = false) UUID idItem,
            Pageable pageable
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
        Optional<List<VtVentaEntity>> FacturasDetalle = vtVentasService.findFacturasWithSpecification(filter, pageable);

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
            @RequestParam(required = false) String secuencia,
            Pageable pageable
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
        Optional<List<VtVentaEntity>> Facturas = vtVentasService.findFacturasWithSpecification(filter, pageable);

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
            @RequestParam(required = false) UUID idItem,
            Pageable pageable
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
        Optional<List<VtVentaEntity>> Facturas = vtVentasService.findFacturasWithSpecification(filter, pageable);
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

    /**
     * Exporta las facturas a un archivo Excel (.xlsx).
     *
     * @param response Objeto HttpServletResponse para configurar y enviar la respuesta HTTP.
     * @param filter Filtro para seleccionar qué facturas exportar.
     * @throws IOException Si ocurre un error durante la escritura del archivo Excel.
     */
    @GetMapping("/exportarFacturasExcel")
    public void exportarFacturasExcel(HttpServletResponse response, VtVentasFilterSpecification filter) throws IOException {
        logger.info("Iniciando la exportación de facturas a Excel con el filtro: {}", filter);

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=facturas_" + currentDateTime + ".xlsx");

        Optional<List<VtVentaEntity>> optionalFacturas = vtVentasService.findFacturasWithSpecification(filter, PageRequest.of(0, Integer.MAX_VALUE));
        List<VtVentaEntity> facturas = optionalFacturas.orElse(new ArrayList<>());

        if (!facturas.isEmpty()) {
            logger.info("Facturas obtenidas satisfactoriamente.");

            try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                XSSFSheet sheet = workbook.createSheet("Facturas");
                XSSFRow headerRow = sheet.createRow(0);

                String[] columnNames = { "ID", "ID Data", "ID Empresa", "Serie", "Secuencia", "Fecha Emisión", "Clave Acceso", "Tipo Venta", "Subtotal", "Total Descuento", "Base Cero", "Base Gravada", "Totales" };
                IntStream.range(0, columnNames.length)
                        .forEach(i -> headerRow.createCell(i).setCellValue(columnNames[i]));

                for (int i = 0; i < facturas.size(); i++) {
                    VtVentaEntity factura = facturas.get(i);
                    XSSFRow row = sheet.createRow(i + 1);

                    row.createCell(0).setCellValue(String.valueOf(factura.getIdVenta()));
                    row.createCell(1).setCellValue(factura.getIdData());
                    row.createCell(2).setCellValue(factura.getIdEmpresa());
                    row.createCell(3).setCellValue(factura.getSerie());
                    row.createCell(4).setCellValue(factura.getSecuencia());
                    row.createCell(5).setCellValue(factura.getFechaEmision().toString());
//                    row.createCell(6).setCellValue(factura.getClaveAcceso());
                    row.createCell(7).setCellValue(factura.getTipoVenta());
//                    row.createCell(8).setCellValue(String.valueOf(factura.getSubtotal()));
//                    row.createCell(9).setCellValue(String.valueOf(factura.getTotalDescuento()));
//                    row.createCell(10).setCellValue(String.valueOf(factura.getBaseCero()));
//                    row.createCell(11).setCellValue(String.valueOf(factura.getBaseGravada()));
                    //row.createCell(12).setCellValue(String.valueOf(factura.getTotales()));
                }

                try (OutputStream os = response.getOutputStream()) {
                    workbook.write(os);
                }
            } catch (IOException e) {
                logger.error("Error al crear el archivo Excel", e);
                throw e;
            }
        } else {
            logger.warn("No se encontraron facturas con los filtros proporcionados.");
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            OutputStream os = response.getOutputStream();
            os.write("No se encontraron facturas con los filtros proporcionados".getBytes());
            os.flush();
            os.close();
        }
    }

    /**
     * Exporta las facturas a un archivo PDF.
     *
     * @param response Objeto HttpServletResponse para configurar y enviar la respuesta HTTP.
     * @param filter Filtro para seleccionar qué facturas exportar.
     * @throws DocumentException Si ocurre un error durante la creación del documento PDF.
     * @throws IOException Si ocurre un error durante la escritura del archivo PDF.
     */
    @GetMapping("/exportarFacturasPDF")
    public void exportarFacturasPDF(HttpServletResponse response, VtVentasFilterSpecification filter) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=facturas_" + LocalDateTime.now() + ".pdf";
        response.setHeader(headerKey, headerValue);


        // Obtener las facturas según el filtro
        Optional<List<VtVentaEntity>> optionalFacturas = vtVentasService.findFacturasWithSpecification(filter, PageRequest.of(0, Integer.MAX_VALUE));

        if (optionalFacturas.isPresent()) {
            List<VtVentaEntity> facturas = optionalFacturas.get();

            // Iniciar el documento PDF
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Crear la tabla y los encabezados del PDF
            PdfPTable table = new PdfPTable(13); // Número de columnas
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // definimos los nombres de las columnas, excluyendo el campo XML
            String[] columnNames = { "ID", "ID Data", "ID Empresa", "Serie", "Secuencia", "Fecha Emisión", "Clave Acceso", "Tipo Venta", "Subtotal", "Total Descuento", "Base Cero", "Base Gravada", "Totales" };

            // Añadir columnas
            for (String columnName : columnNames) {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(Color.LIGHT_GRAY);
                header.setBorderWidth(1);
                header.setPhrase(new Phrase(columnName));
                table.addCell(header);
            }

            // Añadir filas con los datos de las facturas
            for (VtVentaEntity factura : facturas) {
                table.addCell(factura.getIdVenta().toString());
                table.addCell(factura.getIdData());
                table.addCell(factura.getIdEmpresa());
                table.addCell(factura.getSerie());
                table.addCell(factura.getSecuencia());
                table.addCell(factura.getFechaEmision().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                //  table.addCell(factura.getClaveAcceso());
                table.addCell(factura.getTipoVenta());
//                table.addCell(String.valueOf(factura.getSubtotal()));
//                table.addCell(String.valueOf(factura.getTotalDescuento()));
//                table.addCell(String.valueOf(factura.getBaseCero()));
//                table.addCell(String.valueOf(factura.getBaseGravada()));
                //table.addCell(String.valueOf(factura.getTotales()));
            }

            // Añadir la tabla al documento y cerrar
            document.add(table);
            document.close();
        } else {
            // Manejar el caso en el que no se encuentren facturas
            try {
                OutputStream os = response.getOutputStream();
                os.write("No se encontraron facturas con los filtros proporcionados".getBytes());
                os.flush();
                os.close();
            } catch (IOException e) {
                log.error("Error al escribir el archivo PDF", e);
            }

        }
    }

}
