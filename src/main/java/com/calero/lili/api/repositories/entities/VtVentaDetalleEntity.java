package com.calero.lili.api.repositories.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vtVentasDetalle") //invoice_details
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class VtVentaDetalleEntity {

    @Id
    @Column(name = "idVentaDetalle", unique = true, updatable = false, nullable = false)
    private UUID idVentaDetalle;

    @Column
    private String codigoItem;

    @Column
    private String item;

    @Column
    private BigDecimal precioUnitario;

    @Column
    private BigDecimal cantidad;

    @Column
    private BigDecimal descuento;

    @Column(name = "subtotal")
    private BigDecimal subtotal;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_venta") // NOMBRE DEL CAMPO RELACIONADO, PONER EL MIMO NOMBRE QUE TIENE LA TABLA PRINCIPAL
    private VtVentaEntity venta; // NOMBRE DE LA RELACION, ESTE VA EN LA TABLA PRINCIPAL

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_item") // NOMBRE DEL CAMPO RELACIONADO, PONER EL MIMO NOMBRE QUE TIENE LA TABLA PRINCIPAL
    private GeItemsEntity items; // NOMBRE DE LA RELACION, ESTE VA EN LA TABLA PRINCIPAL

}
