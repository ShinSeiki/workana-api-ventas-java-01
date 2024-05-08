package com.calero.lili.api.repositories.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vt_clientes")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VtClientesEntity implements Serializable{

    @Column(name = "id_data")
    private String idData;

    @Id
    @Column(name = "id_cliente", unique = true, updatable = false, nullable = false)
    private UUID idCliente;

    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;

    @Column(name = "numero_identificacion", length = 15,unique=true)
    private String numeroIdentificacion;

    @Column(name = "cliente", columnDefinition = "varchar (300)")
    private String cliente;

    @Column(name = "web")
    private String web;

    @Column(name = "observaciones", columnDefinition = "varchar (300)")
    private String observaciones;

    @Column(name = "tipo_cliente")
    private String tipoCliente;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Direccion> direcciones;

    // Clase interna para encapsular la dirección
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Direccion {
        private String ciudad;
        private String direccion;
        private String telefonos;
        private String contacto;
        private String email;
        //private List<String> emails;
    }

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<VtVentaEntity> vtFacturaEntity = new ArrayList<>();

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_grupo") // NOMBRE DEL CAMPO RELACIONADO PONER EL MISMO QUE TIENE LA TABLA PRINCIPAL
//    private VtClientesGruposEntity grupos; // NOMBRE DE LA RELACION, ESTE VA EN LA TABLA PRINCIPAL

//    @OneToMany(mappedBy = "direcciones" ) // NOMBRE DE LA RELACION viene de la TABLA SECUNDARIA
//    @JsonIgnore
//    private List<VtClientesDireccionesEntity> vtClientesDireccionesEntities = new ArrayList<>();


}

