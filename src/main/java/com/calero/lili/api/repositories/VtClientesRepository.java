package com.calero.lili.api.repositories;

import com.calero.lili.api.repositories.entities.VtClientesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface VtClientesRepository extends JpaRepository<VtClientesEntity, UUID> {

    VtClientesEntity findByIdDataAndIdCliente(String idData, UUID idCliente);
    VtClientesEntity findByIdDataAndNumeroIdentificacion(String idData, String numeroIdentificacion);
}
