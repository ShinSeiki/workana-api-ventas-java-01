package com.calero.lili.api.repositories.projections;

import com.calero.lili.api.repositories.entities.GeItemsEntity;
import com.calero.lili.api.repositories.entities.VtClientesEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface VtClientesProjection {

    String getIdData();
    void setIdData(String idData);

    UUID getIdCliente();
    void setIdCliente(UUID idCliente);

    String getCliente();
    void setCliente(String cliente);

    String getTipoIdentificacion();
    void setTipoIdentificacion(String tipoIdentificacion);

    String getNumeroIdentificacion();
    void setNumeroIdentificacion(String numeroIdentificacion);

    String getWeb();
    void setWeb(String web);

    String getObservaciones();
    void setObservaciones(String observaciones);

    UUID getIdGrupo();
    void setIdGrupo(UUID idGrupo);

    List<VtClientesEntity.Direccion> getDirecciones();
    void setDirecciones(List direccion);

//    String getCiudad();
//    void setCiudad(String ciudad);
//
//    String getContacto();
//    void setContacto(String contacto);
//
//    String getEmail();
//    void setEmail(String email);
//    String getTelefonos();
//    void setTelefonos(String telefonos);

    //LocalDateTime getCreateAt();
    //void setCreateAt(LocalDateTime createAt);

}
