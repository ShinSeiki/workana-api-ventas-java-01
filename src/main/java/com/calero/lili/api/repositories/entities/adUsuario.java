package com.calero.lili.api.repositories.entities;

import java.util.List;

import com.calero.lili.api.dtos.models.IUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name="adUsuarios")
@Data
public class AdUsuario implements IUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @NotBlank
    @Size(min = 4, max = 8)
    @Column(unique = true)
    private String username;

    @NotBlank
    private String password;

    @NotEmpty
    @Email
    @Column(unique = true)
    private String email;

    @NotEmpty
    private String idArea;

    @NotEmpty
    @Column(name = "id_data")
    private String idData;

    @Transient
    private boolean admin;

    // ad_usuarios_roles NO SE VE AQUI EN LAS ENTITIES SOLO SE VE ESTA RELACION

    @ManyToMany
    @JoinTable(
            name = "ad_usuarios_roles",
            joinColumns = @JoinColumn(name="id_usuario"),
            inverseJoinColumns = @JoinColumn(name="id_rol"),
            uniqueConstraints = { @UniqueConstraint(columnNames = {"id_usuario", "id_rol"})})

    private List<AdRol> roles;


}
