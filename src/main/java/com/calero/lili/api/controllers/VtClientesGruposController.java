package com.calero.lili.api.controllers;

import com.calero.lili.api.dtos.vtClientesGrupos.VtClienteGrupoCreationRequestDto;
import com.calero.lili.api.dtos.vtClientesGrupos.VtClienteGrupoCreationResponseDto;
import com.calero.lili.api.services.VtClientesGruposService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1.0/clientes/grupos")
@CrossOrigin(originPatterns = "*")

public class VtClientesGruposController {

    private final VtClientesGruposService vtClientesGruposService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public VtClienteGrupoCreationResponseDto create(
            @RequestBody VtClienteGrupoCreationRequestDto request) {
        return vtClientesGruposService.create(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VtClienteGrupoCreationResponseDto update(@PathVariable("id") UUID id,
                                                    @RequestBody VtClienteGrupoCreationRequestDto request) {
        return vtClientesGruposService.update(id, request);
    }

}
