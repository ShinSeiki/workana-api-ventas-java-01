package com.calero.lili.api.dtos.vtClientes;

import com.calero.lili.api.repositories.entities.VtClientesEntity;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class VtClienteDto {

    private UUID idCliente;

    private String cliente;

    private String tipoIdentificacion;

    private String numeroIdentificacion;

    private String web;

    private String observaciones;

    private UUID idGrupo;

    private String tipoCliente;
    private Boolean Relacionado;

    //habilitar para que venga con varias direcciones
//    List<VtClientesDireccionesEntity> direcciones;

    private List<VtClientesEntity.Direccion> direcciones;

}
