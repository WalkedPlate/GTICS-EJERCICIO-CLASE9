package com.example.gticsejercicioclase7.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Roles {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;


}
