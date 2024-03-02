package com.calero.lili.api.dtos.vtVentas;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Clase FacturaFilter
 *
 * Utiliza el patrón de diseño "Builder Pattern" para crear objetos de filtrado
 * para las entidades de factura y sus detalles.
 *
 * Patrones de Diseño Utilizados:
 * - Builder Pattern
 *
 * @version 1.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VtVentasFilterSpecification {

    private String idData;
    private String idEmpresa;
    private UUID idCliente;
    private String numeroIdentificacion;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaEmisionDesde;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate fechaEmisionHasta;
    private String serie;
    private String secuencia;
    private UUID idItem; // Añadido para soportar filtrado por item en algunos endpoints

    // Campos nuevos para filtrar detalles de factura
    private String codigoItem;
    private String item;
    private BigDecimal precioUnitario;
    private BigDecimal cantidad;
    private BigDecimal descuento;


    /**
     * Constructor Privado
     *
     * Utilizado por la clase Builder interna para construir una instancia de FacturaFilter.
     *
     * @param builder Objeto Builder con los parámetros establecidos.
     */
    private VtVentasFilterSpecification(Builder builder) {
        this.idData = builder.idData;
        this.idEmpresa = builder.idEmpresa;
        this.idCliente = builder.idCliente;
        this.numeroIdentificacion = builder.numeroIdentificacion;
        this.fechaEmisionDesde = builder.fechaEmisionDesde;
        this.fechaEmisionHasta = builder.fechaEmisionHasta;
        this.serie = builder.serie;
        this.secuencia = builder.secuencia;
        this.idItem = builder.idItem; // Añadido para soportar filtrado por item en algunos endpoints
        this.codigoItem = builder.codigoItem;
        this.item = builder.item;
        this.precioUnitario = builder.precioUnitario;
        this.cantidad = builder.cantidad;
        this.descuento = builder.descuento;
    }

    /**
     * Método isEmpty
     *
     * Comprueba si todos los campos del filtro están vacíos.
     *
     * @return boolean Verdadero si todos los campos están vacíos, falso en caso contrario.
     */
    public boolean isEmpty() {
        return this.idData == null &&
                this.idEmpresa == null &&
                this.idCliente == null &&
                (this.numeroIdentificacion == null || this.numeroIdentificacion.isEmpty()) &&
                this.fechaEmisionDesde == null &&
                this.fechaEmisionHasta == null &&
                (this.serie == null || this.serie.isEmpty()) &&
                (this.secuencia == null || this.secuencia.isEmpty()) &&
                this.idItem == null &&  // Añadido para soportar filtrado por item en algunos endpoints
                (this.codigoItem == null || this.codigoItem.isEmpty()) &&
                (this.item == null || this.item.isEmpty()) &&
                this.precioUnitario == null &&
                this.cantidad == null &&
                this.descuento == null;
    }


    /**
     * Clase Builder (Patrón de diseño Builder)
     *
     * Facilita la construcción de un objeto FacturaFilter.
     */
    public static class Builder {
        private String idData;
        private String idEmpresa;
        private UUID idCliente;
        private String numeroIdentificacion;

        private LocalDate fechaEmisionDesde;

        private LocalDate fechaEmisionHasta;
        private String serie;
        private String secuencia;
        private UUID idItem; // Añadido para soportar filtrado por item en algunos endpoints
        private String codigoItem;
        private String item;
        private BigDecimal precioUnitario;
        private BigDecimal cantidad;
        private BigDecimal descuento;

        public Builder() {}
        public Builder idData(String idData) {
            this.idData = idData;
            return this;
        }

        public Builder idEmpresa(String idEmpresa) {
            this.idEmpresa = idEmpresa;
            return this;
        }

        public Builder idCliente(UUID idCliente) {
            this.idCliente = idCliente;
            return this;
        }

        public Builder numeroIdentificacion(String numeroIdentificacion) {
            this.numeroIdentificacion = numeroIdentificacion;
            return this;
        }

        public Builder fechaEmisionDesde(LocalDate fechaEmisionDesde) {
            this.fechaEmisionDesde = fechaEmisionDesde;
            return this;
        }

        public Builder fechaEmisionHasta(LocalDate fechaEmisionHasta) {
            this.fechaEmisionHasta = fechaEmisionHasta;
            return this;
        }

        public Builder serie(String serie) {
            this.serie = serie;
            return this;
        }

        public Builder secuencia(String secuencia) {
            this.secuencia = secuencia;
            return this;
        }

        public Builder idItem(UUID idItem) { // Añadido para soportar filtrado por item en algunos endpoints
            this.idItem = idItem;
            return this;
        }

        // Métodos builder para los nuevos campos
        public Builder codigoItem(String codigoItem) {
            this.codigoItem = codigoItem;
            return this;
        }

        public Builder item(String item) {
            this.item = item;
            return this;
        }

        public Builder precioUnitario(BigDecimal precioUnitario) {
            this.precioUnitario = precioUnitario;
            return this;
        }

        public Builder cantidad(BigDecimal cantidad) {
            this.cantidad = cantidad;
            return this;
        }

        public Builder descuento(BigDecimal descuento) {
            this.descuento = descuento;
            return this;
        }

        public VtVentasFilterSpecification build() {
            return new VtVentasFilterSpecification(this);
        }
    }
}
