package com.calero.lili.api.repositories;

import com.calero.lili.api.dtos.vtClientes.VtClienteReportDto;
import com.calero.lili.api.repositories.entities.VtClientesEntity;
import com.calero.lili.api.repositories.projections.VtClientesProjection;
import com.calero.lili.api.repositories.projections.VtClientesProjectionPrueba;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VtClientesRepository extends JpaRepository<VtClientesEntity, UUID> {

    @Query(value = "SELECT cliente " +
            "FROM vt_clientes " +
            "where " +
            "vt_clientes.numero_identificacion = :numeroIdentificacion LIMIT 1", nativeQuery = true)
    Optional<VtClientesProjection> findfirst(String numeroIdentificacion);



}
