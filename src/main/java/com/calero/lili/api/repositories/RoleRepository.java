package com.calero.lili.api.repositories;

import java.util.Optional;

import com.calero.lili.api.repositories.entities.adRol;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository
        extends CrudRepository<adRol, Long> {
        Optional<adRol> findByName(String name);
}
