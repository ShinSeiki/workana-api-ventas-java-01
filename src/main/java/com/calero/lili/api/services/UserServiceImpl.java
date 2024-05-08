package com.calero.lili.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.calero.lili.api.dtos.adusuarios.*;
import com.calero.lili.api.repositories.RoleRepository;
import com.calero.lili.api.repositories.UserRepository;
import com.calero.lili.api.repositories.projections.UserProjection;
import com.calero.lili.api.models.IUser;
import com.calero.lili.api.repositories.entities.adUsuario;
import com.calero.lili.api.repositories.entities.adRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
// ACTIVAR CONJUNTAMENTE CON AUTH
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    // ACTIVAR CONJUNTAMENTE CON create
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    public UserDto save(adUsuario user) {

        // ACTIVAR CONJUNTAMENTE CON AUTH
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // SIN AUTH
        //user.setPassword(user.getPassword());

        user.setRoles(getRoles(user));
        return DtoMapperUser.builder().setUser(repository.save(user)).build();
    }


/*
    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAllPaginate() {
        List<adUsuario> users = (List<adUsuario>) repository.findAllPaginate();
        return users
                .stream()
                .map(u -> DtoMapperUser.builder().setUser(u).build())
                .collect(Collectors.toList());
    }
*/

    public Page<UserReportDto> findAllPaginate(UsersListFilterDto filters, Pageable pageable) {

        //System.out.println(filters.getFilter());

        Page<UserProjection> page = repository.findAllPaginate(filters.getFilter(),pageable);
        return new PageImpl<>(
                page.getContent()
                        .stream()
                        .map(this::projectionToDtoReport)
                        .collect(Collectors.toList()),
                page.getPageable(),
                page.getTotalElements());
    }

    private UserReportDto projectionToDtoReport(UserProjection projection) {
        UserReportDto dto = new UserReportDto();
        dto.setId(projection.getId());
        dto.setUsername(projection.getUsername());
        dto.setEmail(projection.getEmail());
        //dto.setIdGrupo(projection.getIdGrupo();
        return dto;
    }

    @Transactional(readOnly = true)
    public Optional<UserDto> findById(Long id) {
        return repository.findById(id).map(u -> DtoMapperUser
                .builder()
                .setUser(u)
                .build());

    }



    @Transactional
    public Optional<UserDto> update(UserRequest user, Long id) {
        Optional<adUsuario> o = repository.findById(id);
        adUsuario userOptional = null;
        if (o.isPresent()) {
            adUsuario userDb = o.orElseThrow();
            userDb.setRoles(getRoles(user));
            userDb.setUsername(user.getUsername());
            userDb.setEmail(user.getEmail());
            userOptional = repository.save(userDb);
        }
        return Optional.ofNullable(DtoMapperUser.builder().setUser(userOptional).build());
    }


    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);
    }

    private List<adRol> getRoles(IUser user) {
        Optional<adRol> ou = roleRepository.findByName("ROLE_USER");

        List<adRol> roles = new ArrayList<>();
        if (ou.isPresent()) {
            roles.add(ou.orElseThrow());
        }

        if (user.isAdmin()) {
            Optional<adRol> oa = roleRepository.findByName("ROLE_ADMIN");
            if (oa.isPresent()) {
                roles.add(oa.orElseThrow());
            }
        }
        return roles;
    }

}
