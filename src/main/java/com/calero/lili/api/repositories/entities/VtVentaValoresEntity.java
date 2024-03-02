package com.calero.lili.api.repositories.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Data
@Entity
@Table(name = "vt_ventas_valores")
public class VtVentaValoresEntity {
    @Id
    @Column(name = "id_venta_valores")
    private UUID idVentaValores;
    @Basic
    @Column(name = "id_impuesto")
    private String idImpuesto;
    @Basic
    @Column(name = "id_impuesto_codigo")
    private String idImpuestoCodigo;
    @Basic
    @Column(name = "impuesto_porcentaje")
    private BigDecimal impuestoPorcentaje;
    @Basic
    @Column(name = "base_imponible")
    private BigDecimal baseImponible;
    @Basic
    @Column(name = "valor")
    private BigDecimal valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_venta") // NOMBRE DEL CAMPO RELACIONADO, PONER EL MIMO NOMBRE QUE TIENE LA TABLA PRINCIPAL
    private VtVentaEntity valores; // NOMBRE DE LA RELACION, ESTE VA EN LA TABLA PRINCIPAL

}
