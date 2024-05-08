package com.calero.lili.api.dtos.vtClientes;

import com.calero.lili.api.repositories.entities.VtClientesEntity;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class VtClienteReportDto {

    private UUID idCliente;

    private String cliente;

    private String tipoIdentificacion;

    private String numeroIdentificacion;

    private String web;

    private String observaciones;

    private UUID idGrupo;

    private List<VtClientesEntity.Direccion> direcciones;

    //private String direcciones;
//    private String ciudad;
//    private String contacto;
//    private String email;
//    private String telefonos;


}
