package com.calero.lili.api.controllers;

import com.calero.lili.api.services.VtGenearServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping(value = "api/v1.0/generar")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*")

public class VtGenerarFacturasController {

    private final VtGenearServiceImpl vtGenearService;
    private static final Logger logger = LoggerFactory.getLogger(VtGenerarFacturasController.class);

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void generarFacturas( ) {
        vtGenearService.GenerarFacturas();
    }

}
