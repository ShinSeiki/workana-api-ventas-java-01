package com.calero.lili.api.repositories.projections;

public interface UserProjection {

    Long getId();
    void setId(Long idData);

    String getUsername();
    void setUsername(String username);

    String getEmail();
    void setEmail(String email);

}
