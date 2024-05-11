package com.calero.lili.api.services;

import com.calero.lili.api.repositories.VtVentaRepository;
import com.calero.lili.api.repositories.projections.VtVentasGenerarProjection;
import com.calero.lili.api.repositories.projections.VtVentasProjection;
import com.calero.lili.api.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VtGenearServiceImpl {

    private final VtVentaRepository vtVentaRepository;
    private static final Logger logger = LoggerFactory.getLogger(VtGenearServiceImpl.class);

    public void GenerarFacturas (){

        LocalDate fechaDesde = DateUtils.toLocalDate("01/01/2000");
        LocalDate fechaHasta = DateUtils.toLocalDate("31/12/2030");

        List<VtVentasGenerarProjection> vtVentasGenerarProjections = vtVentaRepository.findAllFacturasGenerar("ABCD", "AB01", fechaDesde, fechaHasta);

        for (VtVentasGenerarProjection factura : vtVentasGenerarProjections) {
            System.out.println(factura.getBaseCero());
        }

    }

}
