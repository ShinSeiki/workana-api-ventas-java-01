package com.calero.lili.api.repositories.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "vt_clientes_direcciones")
public class VtClientesDireccionesEntity {

    @Id
    @Column(name = "id_clientes_direcciones")
    private UUID idClientesDirecciones;

    @Column(name = "predeterminada")
    private Boolean predeterminada;

    @Column(name = "direccion")
    private String direccion;
    
    @Column(name = "ciudad")
    private String ciudad;
    
    @Column(name = "telefonos")
    private String telefonos;
    
    @Column(name = "contacto")
    private String contacto;
    
    @Column(name = "emails")
    private String emails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente") // NOMBRE DEL CAMPO RELACIONADO, PONER EL MIMO NOMBRE QUE TIENE LA TABLA PRINCIPAL
    private VtClientesEntity direcciones; // NOMBRE DE LA RELACION, ESTE VA EN LA TABLA PRINCIPAL

}
