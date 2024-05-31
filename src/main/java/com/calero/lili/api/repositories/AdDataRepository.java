package com.calero.lili.api.repositories;

import com.calero.lili.api.repositories.entities.AdDatasEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface AdDataRepository extends JpaRepository<AdDatasEntity, String> {
    @Transactional
    @Modifying
    @Query("DELETE FROM AdDatasEntity e WHERE e.idData = ?1 ")
    void deleteByIdData(String idData);

    AdDatasEntity findByIdData(String idData);

    // findfirstByIdDataProj
    // BUSCAR registro por id PARA DEVOLVER AL USUARIO LA INFO en projection
   /*
    @Query(value = "SELECT id_data as idData, data as data " +
            "FROM ad_datas " +
            "where id_data = :idData " +
            "LIMIT 1", nativeQuery = true)
    Optional<AdDataProjection> findfirstByIdDataProj(String idData);
    */

    // findAllPaginate
    // buscar registros por filtros para devolver al usuario



//        @Query(
//            value = "SELECT entity " +
//                    "FROM AdDatasEntity entity "+
//                    "WHERE " +
//                    "(:filter IS NULL OR LOWER(entity.idData) LIKE '%' || LOWER(:filter) || '%' ) OR " +
//                    "(:filter IS NULL OR LOWER(entity.data) LIKE '%' || LOWER(:filter) || '%' ) "
//           ,
//            countQuery = "SELECT COUNT(1) "+
//                    "FROM AdDatasEntity entity " +
//                    "WHERE " +
//                    "(:filter IS NULL OR LOWER(entity.idData) LIKE '%' || LOWER(:filter) || '%' ) OR " +
//                    "(:filter IS NULL OR LOWER(entity.data) LIKE '%' || LOWER(:filter) || '%' ) "
//    )


        @Query(
            value = "SELECT entity " +
                    "FROM AdDatasEntity entity "+
                    "WHERE " +
                    "(:filter IS NULL OR entity.idData = :filter) OR " +
                    "(:filter IS NULL OR entity.data = :filter) "
            ,
            countQuery = "SELECT COUNT(1) "+
                    "FROM AdDatasEntity entity " +
                    "WHERE " +
                    "(:filter IS NULL OR entity.idData = :filter) OR " +
                    "(:filter IS NULL OR entity.data = :filter) "
    )
    Page<AdDatasEntity> findAllPaginate(String filter, Pageable pageable);

        //LOWER(CONCAT('%', :filter, '%')))
}
