package com.calero.lili.api.dtos.vtClientes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
public class VtClienteFindByIdDto {

    private UUID idCliente;

    private String cliente;

    private String tipoIdentificacion;

    private String numeroIdentificacion;

    private String web;

    private String observaciones;

    private UUID idGrupo;

    private String tipoCliente;
    private Boolean Relacionado;

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
