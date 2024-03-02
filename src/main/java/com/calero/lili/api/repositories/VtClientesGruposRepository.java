package com.calero.lili.api.repositories;

import com.calero.lili.api.repositories.entities.VtClientesGruposEntity;
import com.calero.lili.api.repositories.projections.VtClientesGruposProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface VtClientesGruposRepository extends JpaRepository<VtClientesGruposEntity, UUID> {


}
