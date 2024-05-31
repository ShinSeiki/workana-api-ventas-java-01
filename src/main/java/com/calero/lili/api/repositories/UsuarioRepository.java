package com.calero.lili.api.repositories;

import java.util.Optional;

import com.calero.lili.api.repositories.entities.AdUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends JpaRepository<AdUsuario, Long> {

        Optional<AdUsuario> findByUsername(String username);

        @Query("select u from AdUsuario u where u.username=?1")
        Optional<AdUsuario> getUserByUsername(String username);

        Page<AdUsuario> findAll(Pageable pageable);

}
