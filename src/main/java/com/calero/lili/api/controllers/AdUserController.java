package com.calero.lili.api.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import com.calero.lili.api.dtos.adusuarios.UserDto;
import com.calero.lili.api.dtos.adusuarios.UserReportDto;
import com.calero.lili.api.dtos.adusuarios.UsersListFilterDto;
import com.calero.lili.api.repositories.entities.adUsuario;
import com.calero.lili.api.dtos.adusuarios.UserRequest;
import com.calero.lili.api.services.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("api/v1.0/users")
@CrossOrigin(originPatterns = "*")

public class AdUserController {

    @Autowired
    private UserServiceImpl service;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody adUsuario user, BindingResult result) {
        if(result.hasErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Valid @RequestBody UserRequest user,
            BindingResult result,
            @PathVariable Long id) {
        if(result.hasErrors()){
            return validation(result);
        }
        Optional<UserDto> o = service.update(user, id);

        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        Optional<UserDto> o = service.findById(id);

        if (o.isPresent()) {
            service.remove(id);
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build();
    }

    // LIST SIN PAGINACION
  //  @GetMapping()
  //  public List<UserDto> list() {
    //     return service.findAllPaginate();
    // }


    // PAGINADO
    @GetMapping()//"page"
    @ResponseStatus(code = HttpStatus.OK)
    public Page<UserReportDto> findAllPaginate (UsersListFilterDto filters,
                                        Pageable pageable){
        log.info("Filters = {}", filters);
        return service.findAllPaginate(filters, pageable);
    }



    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<UserDto> userOptionl = service.findById(id);

        if (userOptionl.isPresent()) {
            return ResponseEntity.ok(userOptionl.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
