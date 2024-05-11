package com.calero.lili.api.repositories;

import com.calero.lili.api.repositories.entities.VtVentaEntity;
import com.calero.lili.api.repositories.projections.VtVentaDetalleProjection;
import com.calero.lili.api.repositories.projections.VtVentasGenerarProjection;
import com.calero.lili.api.repositories.projections.VtVentasProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VtVentaRepository extends JpaRepository<VtVentaEntity, UUID> , JpaSpecificationExecutor<VtVentaEntity> {

    @Transactional
    @Modifying
    @Query("DELETE FROM VtVentaEntity e WHERE e.idData = ?1 AND e.idEmpresa = ?2 AND e.idVenta = ?3")
    void deleteByIdDataAndEmpresaAndIdVenta(String idData, String idEmpresa, UUID idVenta);

    VtVentaEntity findByIdDataAndIdEmpresaAndIdVenta(String idData, String idEmpresa, UUID idVenta);

    @Query("SELECT vtVentaEntity " +
            "FROM VtVentaEntity vtVentaEntity " +
            "where vtVentaEntity.secuencia = :secuencia")
    Optional<VtVentaEntity> findFirstBySequence(String secuencia);

    /*
    @Query(value = "SELECT * " +
            "FROM VtFacturaEntity vtFacturaEntity " +
            "WHERE (vtFacturaEntity.idData = :idData)  AND " +
            "(vtFacturaEntity.idEmpresa = :idEmpresa) AND " +
            "vtFacturaEntity.tipoVenta = :tipoVenta AND " +
            "vtFacturaEntity.serie = :serie AND " +
            "vtFacturaEntity.secuencia = :secuencia LIMIT 1", nativeQuery = true)
    Optional<VtFacturaEntity> findFirst(String idData, String idEmpresa, String tipoVenta, String serie, String secuencia);
     */
/*
    @Query("SELECT vtFacturaEntity.idFactura as idFactura " +
            "FROM VtFacturaEntity vtFacturaEntity " +
            "WHERE (vtFacturaEntity.idData = :idData)  AND " +
            "(vtFacturaEntity.idEmpresa = :idEmpresa) AND " +
            "vtFacturaEntity.tipoVenta = :tipoVenta AND " +
            "vtFacturaEntity.serie = :serie AND " +
            "vtFacturaEntity.secuencia = :secuencia ")
    Optional<VtFacturasExistProjection> findFirst(String idData, String idEmpresa, String tipoVenta, String serie, String secuencia);
 */

    @Query( value= "SELECT vtVentasEntity.id_Data as id_data " +
            "FROM vt_ventas vtVentasEntity " +
            "WHERE (vtVentasEntity.id_Data = :idData)  AND " +
            "(vtVentasEntity.id_Empresa = :idEmpresa) AND " +
            "vtVentasEntity.tipo_Venta = :tipoVenta AND " +
            "vtVentasEntity.serie = :serie AND " +
            "vtVentasEntity.secuencia = :secuencia LIMIT 1", nativeQuery = true)
    Optional<VtVentasProjection> findFirst(String idData, String idEmpresa, String tipoVenta, String serie, String secuencia);

    @Query(
            value = "SELECT " +
                    "vtVentaEntity.idSucursal as idSucursal," +
                    "vtVentaEntity.idVenta as idVenta," +
                    "vtVentaEntity.tipoVenta as tipoVenta," +
                    "vtVentaEntity.serie as serie," +
                    "vtVentaEntity.secuencia as secuencia," +
                    "vtVentaEntity.autorizacionSri as autorizacionSri," +
                    "vtVentaEntity.fechaEmision as fechaEmision," +
                    "clienteEntity.idCliente as idCliente," +
                    "clienteEntity.cliente as cliente," +
                    "vtVentaEntity.tipodoc as tipodoc," +
                    "vtVentaEntity.tipo as tipo," +
                    "vtVentaEntity.codigoDocumento as codigoDocumento," +
                    "vtVentaEntity.items as items," +
                    "vtVentaEntity.fechaVencimiento as fechaVencimiento," +
                    "vtVentaEntity.diasCredito as diasCredito," +
                    "vtVentaEntity.cuotas as cuotas," +
                    "vtVentaEntity.documentoElectronico as documentoElectronico," +
                    "vtVentaEntity.estadoDocumento as estadoDocumento," +
                    "vtVentaEntity.mensaje as mensaje," +
                    "vtVentaEntity.emailEstado as emailEstado," +
                    "vtVentaEntity.formasPagoSri as formasPagoSri," +
                    "vtVentaEntity.idVendedor as idVendedor," +
                    "vtVentaEntity.baseCero as baseCero," +
                    "vtVentaEntity.baseGravada1 as baseGravada1," +
                    "vtVentaEntity.porcentajeIva1 as porcentajeIva1," +
                    "vtVentaEntity.iva1 as iva1," +
                    "vtVentaEntity.baseGravada2 as baseGravada2," +
                    "vtVentaEntity.porcentajeIva2 as porcentajeIva2," +
                    "vtVentaEntity.iva2 as iva2," +
                    "vtVentaEntity.baseNoObjeto as baseNoObjeto," +
                    "vtVentaEntity.baseExenta as baseExenta," +
                    "vtVentaEntity.anulada as anulada," +
                    "vtVentaEntity.impresa as impresa "+
                    "FROM VtVentaEntity vtVentaEntity " +
                    "INNER JOIN VtClientesEntity clienteEntity ON vtVentaEntity.cliente.idCliente = clienteEntity.idCliente " +
                    "WHERE ( vtVentaEntity.idData = :idData)  AND " +
                    "(vtVentaEntity.idEmpresa = :idEmpresa) AND " +
                    "(:codigoDocumento IS NULL OR vtVentaEntity.codigoDocumento = :codigoDocumento) AND "+
                    "(:serie IS NULL OR vtVentaEntity.serie = :serie ) AND "+
                    "(:secuencia IS NULL OR vtVentaEntity.secuencia = :secuencia ) AND "+
                    "( cast(:fechaEmisionDesde as date) is null OR vtVentaEntity.fechaEmision >= :fechaEmisionDesde ) AND " +
                    "( cast(:fechaEmisionHasta as date) is null OR vtVentaEntity.fechaEmision <= :fechaEmisionHasta )",
            countQuery = "SELECT COUNT(1) "+
                    "FROM VtVentaEntity vtVentaEntity "+
                    "INNER JOIN VtClientesEntity clienteEntity ON vtVentaEntity.cliente.idCliente = clienteEntity.idCliente " +
                    "WHERE ( vtVentaEntity.idData = :idData)  AND " +
                    "(vtVentaEntity.idEmpresa = :idEmpresa) AND " +
                    "(:codigoDocumento IS NULL OR vtVentaEntity.codigoDocumento = :codigoDocumento) AND "+
                    "(:serie IS NULL OR vtVentaEntity.serie = :serie ) AND "+
                    "(:secuencia IS NULL OR vtVentaEntity.secuencia = :secuencia ) AND "+
                    "( cast(:fechaEmisionDesde as date) is null OR vtVentaEntity.fechaEmision >= :fechaEmisionDesde ) AND " +
                    "( cast(:fechaEmisionHasta as date) is null OR vtVentaEntity.fechaEmision <= :fechaEmisionHasta )")
    Page<VtVentasProjection> findAllPaginate(String idData, String idEmpresa, LocalDate fechaEmisionDesde, LocalDate fechaEmisionHasta,String codigoDocumento, String serie, String secuencia, Pageable pageable);

    @Query(
            value = "SELECT " +
                    "vtVentaEntity.idVenta AS idVenta," +
                    "vtVentaEntity.tipoVenta AS tipoVenta," +
                    "vtVentaEntity.serie AS serie," +
                    "vtVentaEntity.secuencia AS secuencia," +
                    "vtVentaDetalleEntity.codigoItem AS codigoItem," +
                    "vtVentaDetalleEntity.item AS item," +
                    "vtVentaDetalleEntity.precioUnitario AS precioUnitario, " +
                    "clienteEntity.idCliente AS idCliente, " +
                    "clienteEntity.cliente AS cliente, " +
                    "clienteEntity.numeroIdentificacion AS numeroIdentificacion " +
                    "FROM VtVentaDetalleEntity vtVentaDetalleEntity " +
                    "INNER JOIN VtVentaEntity vtVentaEntity ON vtVentaDetalleEntity.venta.idVenta = vtVentaEntity.idVenta " +
                    "INNER JOIN VtClientesEntity clienteEntity ON vtVentaEntity.cliente.idCliente = clienteEntity.idCliente " +
                    "WHERE (:supplierNumber IS NULL OR clienteEntity.numeroIdentificacion = :supplierNumber) AND " +
                    "(:supplierName IS NULL OR clienteEntity.cliente LIKE '%' || :supplierName || '%' )",
            countQuery = "SELECT COUNT(1) "+
                    "FROM VtVentaDetalleEntity vtVentaDetalleEntity  " +
                    "INNER JOIN VtVentaEntity vtFacturaEntity ON vtVentaDetalleEntity.venta.idVenta = vtFacturaEntity.idVenta " +
                    "INNER JOIN VtClientesEntity clienteEntity ON vtFacturaEntity.cliente.idCliente = clienteEntity.idCliente " +
                    "WHERE (:supplierNumber IS NULL OR clienteEntity.numeroIdentificacion = :supplierNumber) AND " +
                    "(:supplierName IS NULL OR clienteEntity.cliente LIKE '%' || :supplierName || '%' )")
    Page<VtVentaDetalleProjection> findAllPaginateDetails(String supplierNumber, String supplierName, Pageable pageable);

    @Query(
            value = "SELECT " +
                    "vtVentaEntity.idSucursal as idSucursal," +
                    "vtVentaEntity.idVenta as idVenta," +
                    "vtVentaEntity.tipoVenta as tipoVenta," +
                    "vtVentaEntity.serie as serie," +
                    "vtVentaEntity.secuencia as secuencia," +
                    "vtVentaEntity.autorizacionSri as autorizacionSri," +
                    "vtVentaEntity.fechaEmision as fechaEmision," +
                    "vtVentaEntity.tipodoc as tipodoc," +
                    "vtVentaEntity.tipo as tipo," +
                    "vtVentaEntity.codigoDocumento as codigoDocumento," +
                    "vtVentaEntity.documentoElectronico as documentoElectronico," +
                    "vtVentaEntity.estadoDocumento as estadoDocumento," +
                    "vtVentaEntity.emailEstado as emailEstado," +
                    "vtVentaEntity.formasPagoSri as formasPagoSri," +
                    "vtVentaEntity.baseCero as baseCero," +
                    "vtVentaEntity.baseGravada1 as baseGravada1," +
                    "vtVentaEntity.porcentajeIva1 as porcentajeIva1," +
                    "vtVentaEntity.iva1 as iva1," +
                    "vtVentaEntity.baseGravada2 as baseGravada2," +
                    "vtVentaEntity.porcentajeIva2 as porcentajeIva2," +
                    "vtVentaEntity.iva2 as iva2," +
                    "vtVentaEntity.baseNoObjeto as baseNoObjeto," +
                    "vtVentaEntity.baseExenta as baseExenta," +
                    "vtVentaEntity.anulada as anulada," +
                    "vtVentaEntity.impresa as impresa "+
                    "FROM VtVentaEntity vtVentaEntity " +
                    "INNER JOIN VtVentaDetalleEntity vtVentaDetalleEntity ON vtVentaEntity.idVenta = vtVentaDetalleEntity.venta.idVenta " +
                    "WHERE ( vtVentaEntity.idData = :idData)  AND " +
                    "(vtVentaEntity.idEmpresa = :idEmpresa) AND " +
                    "( cast(:fechaEmisionDesde as date) is null OR vtVentaEntity.fechaEmision >= :fechaEmisionDesde ) AND " +
                    "( cast(:fechaEmisionHasta as date) is null OR vtVentaEntity.fechaEmision <= :fechaEmisionHasta )"
    )
    List<VtVentasGenerarProjection> findAllFacturasGenerar(String idData, String idEmpresa, LocalDate fechaEmisionDesde, LocalDate fechaEmisionHasta);

    @Query(
            value = "SELECT " +
                    "vtVentaEntity.idSucursal as idSucursal," +
                    "vtVentaEntity.idVenta as idVenta," +
                    "vtVentaEntity.tipoVenta as tipoVenta," +
                    "vtVentaEntity.serie as serie," +
                    "vtVentaEntity.secuencia as secuencia," +
                    "vtVentaEntity.autorizacionSri as autorizacionSri," +
                    "vtVentaEntity.fechaEmision as fechaEmision," +
                    "clienteEntity.idCliente as idCliente," +
                    "clienteEntity.cliente as cliente," +
                    "vtVentaEntity.tipodoc as tipodoc," +
                    "vtVentaEntity.tipo as tipo," +
                    "vtVentaEntity.codigoDocumento as codigoDocumento," +
                    "vtVentaEntity.items as items," +
                    "vtVentaEntity.fechaVencimiento as fechaVencimiento," +
                    "vtVentaEntity.diasCredito as diasCredito," +
                    "vtVentaEntity.cuotas as cuotas," +
                    "vtVentaEntity.documentoElectronico as documentoElectronico," +
                    "vtVentaEntity.estadoDocumento as estadoDocumento," +
                    "vtVentaEntity.mensaje as mensaje," +
                    "vtVentaEntity.emailEstado as emailEstado," +
                    "vtVentaEntity.formasPagoSri as formasPagoSri," +
                    "vtVentaEntity.idVendedor as idVendedor," +
                    "vtVentaEntity.baseCero as baseCero," +
                    "vtVentaEntity.baseGravada1 as baseGravada1," +
                    "vtVentaEntity.porcentajeIva1 as porcentajeIva1," +
                    "vtVentaEntity.iva1 as iva1," +
                    "vtVentaEntity.baseGravada2 as baseGravada2," +
                    "vtVentaEntity.porcentajeIva2 as porcentajeIva2," +
                    "vtVentaEntity.iva2 as iva2," +
                    "vtVentaEntity.baseNoObjeto as baseNoObjeto," +
                    "vtVentaEntity.baseExenta as baseExenta," +
                    "vtVentaEntity.anulada as anulada," +
                    "vtVentaEntity.impresa as impresa "+
                    "FROM VtVentaEntity vtVentaEntity " +
                    "INNER JOIN VtClientesEntity clienteEntity ON vtVentaEntity.cliente.idCliente = clienteEntity.idCliente " +
                    "WHERE ( vtVentaEntity.idData = :idData)  AND " +
                    "(vtVentaEntity.idEmpresa = :idEmpresa) AND " +
                    "(:codigoDocumento IS NULL OR vtVentaEntity.codigoDocumento = :codigoDocumento) AND "+
                    "(:serie IS NULL OR vtVentaEntity.serie = :serie ) AND "+
                    "(:secuencia IS NULL OR vtVentaEntity.secuencia = :secuencia ) AND "+
                    "( cast(:fechaEmisionDesde as date) is null OR vtVentaEntity.fechaEmision >= :fechaEmisionDesde ) AND " +
                    "( cast(:fechaEmisionHasta as date) is null OR vtVentaEntity.fechaEmision <= :fechaEmisionHasta )"
    )
    List<VtVentasProjection> findAll(String idData, String idEmpresa, LocalDate fechaEmisionDesde, LocalDate fechaEmisionHasta, String codigoDocumento, String serie, String secuencia);


}
