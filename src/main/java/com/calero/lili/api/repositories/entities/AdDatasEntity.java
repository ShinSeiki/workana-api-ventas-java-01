package com.calero.lili.api.repositories.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ad_datas")
public class AdDatasEntity {

    @Id
    @Column(name = "id_data")
    private String idData;
    @Column(name = "data")
    private String data;

}
