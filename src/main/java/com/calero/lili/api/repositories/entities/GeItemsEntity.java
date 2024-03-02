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

    @Id
    @Column(name = "id_item")
    private UUID idItem;

    @Column(name = "codigo_item")
    private String codigoItem;

    @Column(name = "item")
    private String item;

    @OneToMany(mappedBy = "item") // NOMBRE DE LA RELACION viene de la TABLA SECUNDARIA
    private List<VtVentaDetalleEntity> vtFacturaDetalleEntities = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_items_impuestos") // NOMBRE DEL CAMPO RELACIONADO, PONER EL MIMO NOMBRE QUE TIENE LA TABLA PRINCIPAL
    private GeItemsImpuestosEntity geItemsImpuestosEntity; // NOMBRE DE LA RELACION, ESTE VA EN LA TABLA PRINCIPAL

}
