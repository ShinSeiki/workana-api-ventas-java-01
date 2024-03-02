package com.calero.lili.api.repositories;

import com.calero.lili.api.repositories.entities.GeItemsEntity;
import com.calero.lili.api.repositories.projections.GeItemsProjection;
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
public interface GeItemsRepository extends JpaRepository<GeItemsEntity, UUID> {

    @Query(value = "SELECT item " +
            "FROM ge_items " +
            "where  " +
            "ge_items.codigo_item = :codigo_item LIMIT 1", nativeQuery = true)
    Optional<GeItemsProjection> findfirst(String codigo_item);

}
