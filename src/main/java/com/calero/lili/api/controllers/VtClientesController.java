package com.calero.lili.api.controllers;

import com.calero.lili.api.dtos.vtClientes.*;
import com.calero.lili.api.services.VtClientesServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public VtClienteCreationResponseDto create(
            @Valid @RequestBody VtClienteCreationRequestDto request) {
        return vtClientesService.create(request);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public VtClienteCreationResponseDto update(
            @PathVariable("id") UUID id,
                                               @RequestBody VtClienteCreationRequestDto request) {
        return vtClientesService.update(id, request);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
        public VtClienteFindByIdDto findById(
                @PathVariable("id") UUID id) {
        return vtClientesService.findById(id);
    }

}
