package com.calero.lili.api.dtos.vtClientes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

@Data
public class VtClienteCreationRequestDto {

    private String cliente;

    private String tipoIdentificacion;

    private String numeroIdentificacion;

    private String web;

    private String observaciones;

    private UUID idGrupo;

    private String tipoCliente;
    private String relacionado;

    // para crear en lista los clientes
    public String ciudad;
    public String direccion;
    public String telefonos;
    public String contacto;
    public String emails;

    // para crear de uno en uno los clientes
    private List<Direccion> direcciones;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Direccion {
        private String ciudad;
        private String direccion;
        private String telefonos;
        private String contacto;
        private String email;
    }

}
