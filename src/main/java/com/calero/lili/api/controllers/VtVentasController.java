package com.calero.lili.api.controllers;

import com.calero.lili.api.annotations.VtVentasListFilter;
import com.calero.lili.api.dtos.vtVentas.VtVentaListFilterDto;
import com.calero.lili.api.dtos.vtVentas.VtVentaCreationRequestDto;
import com.calero.lili.api.dtos.vtVentas.VtVentaCreationResponseDto;
import com.calero.lili.api.dtos.vtVentas.VtVentaDetalleSummaryDto;
import com.calero.lili.api.dtos.vtVentas.VtVentaResponseFindByIdDto;
import com.calero.lili.api.dtos.vtVentas.VtVentaDetalleListFilterDto;
import com.calero.lili.api.services.VtVentasServiceImpl;
import com.lowagie.text.DocumentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public VtVentaReportPageDto findAllVentasPaginate(@PathVariable("idData") String idData,
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

    @GetMapping("{idData}/{idEmpresa}/exportarFacturasPDF")
    public void exportarFacturasPDF(HttpServletResponse response,
                                    @PathVariable("idData") String idData,
                                    @PathVariable("idEmpresa") String idEmpresa,
                                    VtVentaListFilterDto filters) throws DocumentException, IOException {
        vtVentasService.exportarFacturasPDF(response, idData, idEmpresa, filters);
    }}
