package com.calero.lili.api.repositories;

import com.calero.lili.api.repositories.entities.VtClientesEntity;
import com.calero.lili.api.repositories.projections.VtClientesProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface VtClientesRepository extends JpaRepository<VtClientesEntity, UUID> {

    @Transactional
    @Modifying
    @Query("DELETE FROM VtClientesEntity e WHERE e.idData = ?1 AND e.idCliente = ?2")
    void deleteByIdDataAndIdCAndCliente(String idData, UUID idCliente);

    VtClientesEntity findByIdDataAndIdCliente(String idData, UUID idCliente);

    /*
    @Query(value = "SELECT * " +
            "FROM vt_clientes where vt_clientes.id_cliente = :id LIMIT 1", nativeQuery = true)
    Optional<VtClientesProjection> findfirstByIdDataAndNumberProjection(String idData, String numeroIdentificacion);
     */

    //@Query(value = "SELECT * " +
    //        "FROM vt_clientes where vt_clientes.id_cliente = :id LIMIT 1", nativeQuery = true)
    //Optional<VtClientesProjection> findfirstByUuidProjection(UUID id);

//    @Query(value = "SELECT id_data as id_Data " +
//            "FROM vt_clientes " +
//            "where vt_clientes.id_data = :idData and " +
//            "vt_clientes.numero_identificacion = :numeroIdentificacion LIMIT 1", nativeQuery = true)
//    Optional<VtClientesProjection> findfirst(String idData, String numeroIdentificacion);

    VtClientesEntity findByIdDataAndNumeroIdentificacion(String idData, String numeroIdentificacion);

    @Query(
            value = "SELECT entity.idData as idData," +
                    "entity.idCliente as idCliente ," +
                    "entity.cliente as cliente," +
                    "entity.tipoIdentificacion as tipoIdentificacion," +
                    "entity.numeroIdentificacion as numeroIdentificacion," +
                    "entity.web as web," +
                    "entity.observaciones as observaciones," +
                    "entity.direcciones as direcciones "+
                    "FROM VtClientesEntity entity " +
                    "WHERE (entity.idData = :idData) AND " +
                    "((:filtro IS NULL OR lower(entity.cliente) LIKE '%' || lower(:filtro) || '%' ) OR " +
                    "(:filtro IS NULL OR lower(entity.numeroIdentificacion) LIKE '%' || lower(:filtro) || '%' )) ",
            countQuery = "SELECT COUNT(1) FROM VtClientesEntity entity "+
                    "WHERE (entity.idData = :idData) AND " +
                    "((:filtro IS NULL OR lower(entity.cliente) LIKE '%' || lower(:filtro) || '%' ) OR " +
                    "(:filtro IS NULL OR lower(entity.numeroIdentificacion) LIKE '%' || lower(:filtro) || '%' )) "
    )
    Page<VtClientesProjection> findAllPaginate(String idData, String filtro, Pageable pageable);


//    @Query(
//            value = "SELECT " +
//                    "vtClientes.idCliente as idCliente, "+
//                    "vtClientes.numeroIdentificacion as numeroIdentificacion, " +
//                    "vtClientes.cliente as cliente," +
//                    "vtClientes.tipoCliente as tipoCliente " +
//                    "FROM VtClientesEntity as vtClientes "+
//                    "WHERE (vtClientes.idData = :idData) AND " +
//                    "((:filtro IS NULL OR lower(vtClientes.cliente) LIKE '%' || lower(:filtro) || '%' ) OR " +
//                    "(:filtro IS NULL OR lower(vtClientes.numeroIdentificacion) LIKE '%' || lower(:filtro) || '%' )) ")
//    List<VtClientesProjectionPrueba> findAll(String idData, String filtro);

//    @Query(
//            value = "SELECT " +
//                    "CAST(vt_clientes.id_cliente as varchar) as idCliente, "+
//                    "vt_clientes.numero_identificacion as numeroIdentificacion, " +
//                    "vt_clientes.cliente as cliente," +
//                    "vt_clientes.tipo_cliente as tipoCliente " +
//                    "FROM vt_clientes "+
//                    "WHERE (vt_clientes.id_Data = :idData) AND " +
//                    "((:filtro IS NULL OR lower(vt_clientes.cliente) LIKE '%' || lower(:filtro) || '%' ) OR " +
//                    "(:filtro IS NULL OR lower(vt_clientes.numero_Identificacion) LIKE '%' || lower(:filtro) || '%' )) " +
//                    "LIMIT 100", nativeQuery = true
//    )
//    List<VtClientesProjectionPrueba> findAll(String idData, String filtro);

}
//"order by entity.numeroIdentificacion asc"