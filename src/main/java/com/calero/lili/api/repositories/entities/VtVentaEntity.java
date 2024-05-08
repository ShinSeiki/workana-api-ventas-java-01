package com.calero.lili.api.repositories.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
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

    @Column(name = "id_data")
    private String idData;
    @Column(name = "id_empresa")
    private String idEmpresa;
    @Column(name = "id_sucursal")
    private String idSucursal;

    @Id
    @Column(name = "idVenta", unique = true, updatable = false, nullable = false)
    private UUID idVenta;

    @Column(name = "tipo_venta")
    private String tipoVenta;
    @Column(name = "serie")
    private String serie;
    @Column(name = "secuencia")
    private String secuencia;
    @Column(name = "autorizacion_sri")
    private String autorizacionSri;
    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;
    @Column(name = "tipodoc")
    private String tipodoc;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "codigo_documento")
    private String codigoDocumento;
    @Column(name = "fecha_anulacion")
    private  LocalDate fechaAnulacion;



    // info credito
    @Column(name = "dias_credito")
    private Integer diasCredito;
    @Column(name = "fecha_vencimiento")
    private  LocalDate fechaVencimiento;
    @Column(name = "cuotas")
    private Integer cuotas;

    @Column(name = "documento_electronico")
    private String documentoElectronico;



    @Column(name = "estado_documento")
    private String estadoDocumento;
    @Column(name = "email_estado")
    private String emailEstado;

    @Column(name = "mensaje")
    private String mensaje;
    @Column(name = "xml")
    private String xml;

    @Column(name = "items")
    private Integer items;

    // VALORES
    @Column(name = "base_gravada_1")
    private BigDecimal baseGravada1;
    @Column(name = "porcentaje_iva_1")
    private int porcentajeIva1;
    @Column(name = "iva_1")
    private BigDecimal iva1;
    @Column(name = "base_gravada_2")
    private BigDecimal baseGravada2;
    @Column(name = "porcentaje_iva_2")
    private int porcentajeIva2;
    @Column(name = "iva_2")
    private BigDecimal iva2;

    @Column(name = "baseCero")
    private BigDecimal baseCero;
    @Column(name = "base_no_objeto")
    private BigDecimal baseNoObjeto;
    @Column(name = "base_exenta")
    private BigDecimal baseExenta;

    private BigDecimal subtotal;
    private BigDecimal descuento;
    private BigDecimal total;

    @Column(name = "id_vendedor")
    private Integer idVendedor;
    @Column(name = "id_zona")
    private Integer idZona;

    @Column(name = "anulada")
    private String anulada;

    @Column(name = "impresa")
    private Boolean impresa;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private ClienteDatos clienteDatos; // ABAJO DEFINO LA LISTA CLIENTEDATOS

    // Clase interna para encapsular los datos del cliente
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ClienteDatos {
        private String tipoIdentificacion;
        private String numeroIdentificacion;
        private String cliente;
        private String direccion;
        private String telefonos;
        private String tipoCliente;
        private String relacionado;
        private String email;
    }

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<InformacionAdicional> informacionAdicional;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InformacionAdicional {
        private String nombre;
        private String valor;
    }

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<FormasPagoSri> formasPagoSri;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FormasPagoSri {
        private String formaPago;
        private BigDecimal valor;
        private String plazo;
        private String unidadTiempo;
    }
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private DatosDocumentoElectronico datosDocumentoElectronico;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DatosDocumentoElectronico {
        private String contribuyenteEspecial;
        private String obligadoContabilidad;
        private String agenteRetencion;
        private String tipoContribuyente;
        private String tipoEmision;
        private String ambiente;
        private  LocalDate fechaAutorizacion;
        private String claveAcceso;
    }

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private DatosFacturaModificada datosFacturaModificada;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DatosFacturaModificada {
        private String motivo;
        private String modificadaTipoVenta;
        private String modificadaSerie;
        private String modificadaSecuencia;

    }

//    @Type(type = "jsonb")
//    @Column(columnDefinition = "jsonb")
//    private List<DetalleItems> detalleItems;
//
//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class DetalleItems {
//        private UUID idVentaDetalle;
//        private UUID idItem;
//        private Integer itemOrden;
//        private String codigoItem;
//        private String item;
//        private BigDecimal cantidad;
//        private BigDecimal precioUnitario;
//        private BigDecimal dsctoItem;
//        private BigDecimal descuentoItem;
//        private BigDecimal subtotalItem;
//
//        private Integer idBodega;
//        private String detalle;
//        private String adicdet1;
//        private String adicdet2;
//        private String adicdet3;
//        private String codigoBarras;
//        private String imagen;
//        private Integer idMedida;
//        private String medida;
//        private String civa;
//        private Boolean iva;
//        private BigDecimal viva;
//        private String cice;
//        private BigDecimal ice;
//
//        private Integer idVendedor;
//    }

    @OneToMany(mappedBy = "venta" ) // NOMBRE DE LA RELACION viene de la TABLA SECUNDARIA
    @JsonIgnore
    private List<VtVentaDetalleEntity> ventaDetalleEntities = new ArrayList<>();

    //@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private VtClientesEntity cliente;

}
