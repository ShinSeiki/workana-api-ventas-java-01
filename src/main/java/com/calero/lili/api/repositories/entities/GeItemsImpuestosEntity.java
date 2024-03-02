package com.calero.lili.api.repositories.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "ge_items_impuestos")
public class GeItemsImpuestosEntity {

    @Id
    @Column(name = "id_items_impuestos")
    private UUID idItemsImpuestos;

    @Column(name = "id_impuesto")
    private String idImpuesto;
    
    @Column(name = "id_impuesto_codigo")
    private String idImpuestoCodigo;

    @Column(name = "impuesto_porcentaje")
    private BigDecimal impuestoPorcentaje;

    @OneToMany(mappedBy = "geItemsImpuestosEntity" ) // NOMBRE DE LA RELACION viene de la TABLA SECUNDARIA
    private List<GeItemsEntity> GeItemsEntity = new ArrayList<>();

}
