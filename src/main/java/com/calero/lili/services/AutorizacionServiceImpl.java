package com.calero.lili.services;

import autorizacion.ws.sri.gob.ec.*;
import com.calero.lili.dtos.autorizacion.AutorizacionRequestDto;
import com.calero.lili.dtos.autorizacion.AutorizacionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AutorizacionServiceImpl {
    public AutorizacionResponseDto consulta(AutorizacionRequestDto request) {

        String claveAccesoComprobante = request.getClaveAcceso();

        AutorizacionComprobantesOfflineService service = new AutorizacionComprobantesOfflineService(request.getAmbiente());
        AutorizacionComprobantesOffline port = service.getAutorizacionComprobantesOfflinePort();
        RespuestaComprobante result = port.autorizacionComprobante(claveAccesoComprobante);

        System.out.println("Comprobantes: "+result.getNumeroComprobantes());
        System.out.println("Clave de acceso: "+result.getClaveAccesoConsultada());
        System.out.println("Autorizaciones: "+result.getAutorizaciones());
        System.out.println(result.getAutorizaciones().getAutorizacion());

        return null;
    }

}
