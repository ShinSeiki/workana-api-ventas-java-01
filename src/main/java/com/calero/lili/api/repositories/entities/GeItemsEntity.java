package com.calero.lili.api.repositories.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ge_items")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GeItemsEntity {

    @Column(name = "id_data")
    private String idData;

    @Column(name = "id_empresa")
    private String idEmpresa;

    @Id
    @Column(name = "id_item")
    private UUID idItem;

    @Column(name = "codigo_item")
    private String codigoItem;

    @Column(name = "codigo_barras")
    private String codigoBarras;

    @Column(name = "item")
    private String item;


//    @Column(name = "cmarca")
//    private String cmarca;

    //@Column(name = "id_medida")
    //private int idMedida;

//    @Column(name = "id_grupo")
//    private Integer idGrupo;

    @OneToMany(mappedBy = "item") // NOMBRE DE LA RELACION viene de la TABLA SECUNDARIA
    private List<VtVentaDetalleEntity> vtVentaDetalleEntities = new ArrayList<>();

    // Clase interna para encapsular el detalle adicional

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<DetalleAdicional> detallesAdicionales;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Impuesto> impuestos;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetalleAdicional {
        private String nombre;
        @Column(length = 300)
        private String valor;
    }

    // arriba comentado el campo impuestos jsonb
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Impuesto {
        private String impuesto;
        private String codigo;
        private String porcentaje;
    }
}
