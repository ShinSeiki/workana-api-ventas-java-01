package com.calero.lili.api.repositories.projections;

import java.util.UUID;

public interface VtClientesGruposProjection {
    UUID getIdGrupo();
  void setIdGrupo(UUID idGrupo);

  String getGrupo();
  void setGrupo(String grupo);


}
