package com.calero.lili.api.repositories;

import com.calero.lili.api.repositories.entities.VtVentaEntity;
import com.calero.lili.api.repositories.projections.VtVentaDetalleProjection;
import com.calero.lili.api.repositories.projections.VtVentasProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VtVentaRepository extends JpaRepository<VtVentaEntity, UUID> , JpaSpecificationExecutor<VtVentaEntity> {

    @Query( value= "SELECT vtFacturaEntity.id_Data as id_data " +
            "FROM Vt_Facturas vtFacturaEntity " +
            "WHERE " +
            "vtFacturaEntity.secuencia = :secuencia LIMIT 1", nativeQuery = true)
    Optional<VtVentasProjection> findFirst(String secuencia);

}
