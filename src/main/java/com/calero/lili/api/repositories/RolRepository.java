package com.calero.lili.api.repositories;

import java.util.Optional;

import com.calero.lili.api.repositories.entities.AdRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RolRepository extends JpaRepository<AdRol, Long> {
        Optional<AdRol> findByName(String name);
}
