package com.calero.lili.api.repositories.projections;

import com.calero.lili.api.repositories.entities.VtClientesEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface VtClientesProjection {

    UUID getIdCliente();
    void setIdCliente(UUID idCliente);

    String getCliente();
    void setCliente(String cliente);

    String getNumeroIdentificacion();
    void setNumeroIdentificacion(String numeroIdentificacion);

    UUID getIdGrupo();
    void setIdGrupo(UUID idGrupo);

}
