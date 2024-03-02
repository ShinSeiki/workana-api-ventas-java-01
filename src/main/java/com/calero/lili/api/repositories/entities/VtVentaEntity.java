package com.calero.lili.api.repositories.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vtVentas")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VtVentaEntity {

    @Id
    @Column(name = "idVenta", unique = true, updatable = false, nullable = false)
    private UUID idVenta;

    @Column(name = "secuencia")
    private String secuencia;

    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    @Column(name = "descuento")
    private BigDecimal totalDescuento;

    @Column(name = "xml")
    private String xml;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "items")
    private BigDecimal items;

    @OneToMany(mappedBy = "venta" ) // NOMBRE DE LA RELACION viene de la TABLA SECUNDARIA
    @JsonIgnore
    private List<VtVentaDetalleEntity> ventaDetalleEntities = new ArrayList<>();

    @OneToMany(mappedBy = "valores" ) // NOMBRE DE LA RELACION viene de la TABLA SECUNDARIA
    @JsonIgnore
    private List<VtVentaValoresEntity> vtVentaValoresEntities = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private VtClientesEntity cliente;

}
