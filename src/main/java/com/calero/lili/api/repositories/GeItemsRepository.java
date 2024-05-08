package com.calero.lili.api.repositories;

import com.calero.lili.api.repositories.entities.GeItemsEntity;
import com.calero.lili.api.repositories.projections.GeItemsProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
@Repository
public interface GeItemsRepository extends JpaRepository<GeItemsEntity, UUID> {

    @Transactional
    @Modifying
    @Query("DELETE FROM GeItemsEntity e WHERE e.idData = ?1 AND e.idEmpresa = ?2 AND e.idItem = ?3")
    void deleteByIdDataAndEmpresaAndIdItem(String idData, String idEmpresa, UUID idItem);

    GeItemsEntity findByIdDataAndIdEmpresaAndIdItem(String idData, String idEmpresa, UUID idItem);

//    @Query(value = "SELECT id_data as id_Data " +
//            "FROM ge_items " +
//            "where ge_items.id_data = :idData and " +
//            "ge_items.id_empresa = :idEmpresa and " +
//            "ge_items.codigo_item = :codigo_item LIMIT 1", nativeQuery = true)
//    Optional<GeItemsProjection> findFirstByIdDataAndIdEmpresaAndCodigoItem(String idData, String idEmpresa, String codigo_item);

    GeItemsEntity findByIdDataAndIdEmpresaAndCodigoItem(String idData, String idEmpresa, String codigo_item);
    @Query(
            value = "SELECT " +
                    "entity.idData as idData," +
                    "entity.idEmpresa as idEmpresa ," +
                    "entity.idItem as idItem ," +
                    "entity.codigoItem as codigoItem," +
                    "entity.codigoBarras as codigoBarras," +
                    "entity.detallesAdicionales as detallesAdicionales," +
                    "entity.impuestos as impuestos," +
                    "entity.item as item " +
                    "FROM GeItemsEntity entity " +
                    "WHERE ( entity.idData = :idData)  AND " +
                    "(entity.idEmpresa = :idEmpresa) AND "+
                    "(" +
                    "(:filtro IS NULL OR lower(entity.item) LIKE '%' || lower(:filtro) || '%' ) OR " +
                    "(:filtro IS NULL OR lower(entity.codigoBarras) LIKE '%' || lower(:filtro) || '%' ) OR " +
                    "(:filtro IS NULL OR lower(entity.codigoItem) LIKE '%' || lower(:filtro) || '%' ) " +
                    ") "
            ,
            countQuery = "SELECT COUNT(1) "+
                    "FROM GeItemsEntity entity "+
                    "WHERE ( entity.idData = :idData)  AND " +
                    "(entity.idEmpresa = :idEmpresa) AND "+
                    "(" +
                    "(:filtro IS NULL OR lower(entity.item) LIKE '%' || lower(:filtro) || '%' ) OR " +
                    "(:filtro IS NULL OR lower(entity.codigoBarras) LIKE '%' || lower(:filtro) || '%' ) OR " +
                    "(:filtro IS NULL OR lower(entity.codigoItem) LIKE '%' || lower(:filtro) || '%' ) " +
                    ") "
    )
    Page<GeItemsProjection> findAllPaginate(String idData, String idEmpresa, String filtro, Pageable pageable);

//    @Query(
//            value = "SELECT " +
//                    "CAST(ge_items.id_item as varchar) as idItem, "+
//                    "ge_items.item as item, " +
//                    "ge_items.codigo_item as codigoItem, " +
//                    "CAST(ge_items.detalles_adicionales as varchar)  as detallesAdicionales, " +
//                    "CAST(ge_items.impuestos as varchar)  as impuestos, " +
//                    "100 as precioUnitario," +
//                    "10 as descuentoPorcentaje," +
//                    "0 as descuentoValor " +
//                    "FROM ge_items "+
//                    "WHERE (ge_items.id_Data = :idData) AND " +
//                    "((:filtro IS NULL OR lower(ge_items.codigo_item) LIKE '%' || lower(:filtro) || '%' ) OR " +
//                    "(:filtro IS NULL OR lower(ge_items.item) LIKE '%' || lower(:filtro) || '%' )) " +
//                    "LIMIT 100", nativeQuery = true
//    )
//    List<GeItemsProjection> findAll(String idData, String filtro);

}
