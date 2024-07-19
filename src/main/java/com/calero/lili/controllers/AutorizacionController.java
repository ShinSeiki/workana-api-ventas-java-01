package com.calero.lili.controllers;

import com.calero.lili.dtos.autorizacion.AutorizacionRequestDto;
import com.calero.lili.dtos.autorizacion.AutorizacionResponseDto;
import com.calero.lili.services.AutorizacionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1.0/ws-consulta")
@CrossOrigin(originPatterns = "*")

public class AutorizacionController {

    private final AutorizacionServiceImpl consultaClaveAccesoService;
    @GetMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public AutorizacionResponseDto create(
            @RequestBody AutorizacionRequestDto request) {
        return consultaClaveAccesoService.consulta(request);
    }

}
