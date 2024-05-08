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
    //@GeneratedValue
    private UUID idVentaDetalle;

    // va abajo
    //@Column
    //private UUID idItem;

    @Column
    private String codigoItem;

    @Column
    private String item;

    @Column
    private BigDecimal precioUnitario;

    @Column
    private BigDecimal cantidad;

    @Column
    private BigDecimal taxAmount;

    @Column
    private BigDecimal descuento;

    @Column(name = "id_bodega")
    private Integer idBodega;

    @Column(name = "item_orden")
    private Integer itemOrden;

    @Column(name = "adicdet1")
    private String adicdet1;

    @Column(name = "adicdet2")
    private String adicdet2;

    @Column(name = "adicdet3")
    private String adicdet3;

    @Column(name = "cbarras")
    private String cbarras;

    @Column(name = "limagen")
    private String limagen;

    @Column(name = "id_medida")
    private Integer idMedida;

    @Column(name = "medida")
    private String medida;

    @Column(name = "civa")
    private String civa;

    @Column(name = "iva")
    private Boolean iva;

    @Column(name = "viva")
    private BigDecimal viva;

    @Column(name = "cice")
    private String cice;

    @Column(name = "ice")
    private BigDecimal ice;

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    @Column(name = "dscto")
    private BigDecimal dscto;

    @Column(name = "id_vendedor")
    private Integer idVendedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_venta") // NOMBRE DEL CAMPO RELACIONADO, PONER EL MIMO NOMBRE QUE TIENE LA TABLA PRINCIPAL
    private VtVentaEntity venta; // NOMBRE DE LA RELACION, ESTE VA EN LA TABLA PRINCIPAL

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_item") // NOMBRE DEL CAMPO RELACIONADO, PONER EL MIMO NOMBRE QUE TIENE LA TABLA PRINCIPAL
    private GeItemsEntity items; // NOMBRE DE LA RELACION, ESTE VA EN LA TABLA PRINCIPAL

}
