package com.calero.lili.api.repositories.entities;

import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "vt_clientes_grupos")
public class VtClientesGruposEntity {

    @Id
    @Column(name = "id_grupo")
    private UUID idGrupo;
         
    @Column(name = "grupo")
    private String grupo;

    @OneToMany(mappedBy = "grupos" ) // NOMBRE DE LA RELACION viene de la TABLA SECUNDARIA
    private List<VtClientesEntity> vtClientesEntities = new ArrayList<>();

}
