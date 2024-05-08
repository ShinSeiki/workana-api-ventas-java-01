package com.calero.lili.api.dtos.vtClientes;

import com.calero.lili.api.repositories.entities.VtClientesEntity;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    private List<VtClientesEntity.Direccion> direcciones;

}
