package com.calero.lili.api.repositories;

import java.util.Optional;

import com.calero.lili.api.repositories.entities.adUsuario;
import com.calero.lili.api.repositories.projections.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<adUsuario, Long> {

        Optional<adUsuario> findByUsername(String username);

        @Query("select u from adUsuario u where u.username=?1")
        Optional<adUsuario> getUserByUsername(String username);

        //Page<adUsuario> findAllPaginate(Pageable pageable);

        @Query(
                value = "SELECT entity.id as id," +
                        "entity.username as username ," +
                        "entity.email as email " +
                        "FROM adUsuario entity "+
                        "WHERE " +
                        "(:filter IS NULL OR lower(entity.username) LIKE '%' || lower(:filter) || '%' ) OR " +
                        "(:filter IS NULL OR lower(entity.email) LIKE '%' || lower(:filter) || '%' )",
                countQuery = "SELECT COUNT(1) FROM adUsuario entity " +
                        "WHERE " +
                        "(:filter IS NULL OR lower(entity.username) LIKE '%' || lower(:filter) || '%' ) OR " +
                        "(:filter IS NULL OR lower(entity.email) LIKE '%' || lower(:filter) || '%' )")
        Page<UserProjection> findAllPaginate(String filter, Pageable pageable);

}

