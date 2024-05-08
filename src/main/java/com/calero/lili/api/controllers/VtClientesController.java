package com.calero.lili.api.controllers;

import com.calero.lili.api.dtos.errors.ListCreationResponseDto;
import com.calero.lili.api.dtos.vtClientes.*;
import com.calero.lili.api.services.VtClientesServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "api/v1.0/clientes")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*")

public class VtClientesController {

    private final VtClientesServiceImpl vtClientesService;

    @PostMapping("{idData}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public VtClienteCreationResponseDto create(
            @PathVariable("idData") String idData,
            @Valid @RequestBody VtClienteCreationRequestDto request) {
        return vtClientesService.create(idData, request);
    }

    @PostMapping("/createList/{idData}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ListCreationResponseDto createList(
            @PathVariable("idData") String idData,
            @Valid @RequestBody VtClienteListCreationRequestDto request) {
        System.out.println(request);
        return vtClientesService.createListCliente(idData, request);
    }

    @PutMapping("{idData}/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VtClienteCreationResponseDto update(
            @PathVariable("idData") String idData,
            @PathVariable("id") UUID id,
                                               @RequestBody VtClienteCreationRequestDto request) {
        return vtClientesService.update(idData, id, request);
    }

    @DeleteMapping("{idData}/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable("idData") String idData,
            @PathVariable("id") UUID id) {
            vtClientesService.delete(idData, id);
    }

    @GetMapping("{idData}/{id}")
    @ResponseStatus(HttpStatus.OK)
        public VtClienteDto findById(
                @PathVariable("idData") String idData,
                @PathVariable("id") UUID id) {
        return vtClientesService.findById(idData, id);
    }

        @GetMapping("{idData}")
        @ResponseStatus(code = HttpStatus.OK)
        public Page<VtClienteReportDto> findAllPaginate (@PathVariable("idData") String idData,
                                                         VtClienteFilterDto filters,
                                                         Pageable pageable){
            return vtClientesService.findAllPaginate(idData, filters, pageable);
        }



}
