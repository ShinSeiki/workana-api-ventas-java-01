package com.calero.lili.api.dtos.vtClientes;

import com.calero.lili.api.repositories.entities.VtClientesEntity;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class VtClienteCreationListRequestDto {

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
    public String email;

    // habilitar para q vengan varios emails
//    public List<String> emails;

    //habilitar para que venga con varias direcciones
    // para crear de uno en uno los clientes
//    List<VtClienteDirecciones> direcciones;

//    private List<VtClientesEntity.Direccion> direcciones;

}