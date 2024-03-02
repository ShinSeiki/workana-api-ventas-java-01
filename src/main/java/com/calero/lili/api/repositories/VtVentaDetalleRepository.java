package com.calero.lili.api.repositories;

import com.calero.lili.api.repositories.entities.VtVentaDetalleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VtVentaDetalleRepository extends JpaRepository<VtVentaDetalleEntity, UUID> , JpaSpecificationExecutor<VtVentaDetalleEntity> {
    @Modifying
    @Query(value="Delete from VtVentaDetalleEntity where venta.idVenta= :id")
    void deleteAllByIdFactura(UUID id);

}
