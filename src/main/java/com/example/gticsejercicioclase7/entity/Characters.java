package com.example.gticsejercicioclase7.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "characters")
public class Characters {

    @Id
    @Column(name = "id")
    private Integer id;

    @NotBlank
    @Size(min=3, max=100, message = "el nombre debe tener entre 3 y 100 caracteres")
    @Column(name = "name")
    private String name;

    @NotBlank
    @Size(min=3, max=100, message = "la url debe tener entre 3 y 100 caracteres")
    @Column(name = "url")
    private String url;

    @Size(max=100, message = "se permite un máximo de 100 caracteres")
    @Column(name = "identity")
    private String identity;

    @Size(max=100, message = "se permite un máximo de 100 caracteres")
    @Column(name = "align")
    private String align;

    @Size(max=100, message = "se permite un máximo de 100 caracteres")
    @Column(name = "eye")
    private String eye;

    @Size(max=100, message = "se permite un máximo de 100 caracteres")
    @Column(name = "hair")
    private String hair;

    @Size(max=100, message = "se permite un máximo de 100 caracteres")
    @Column(name = "sex")
    private String sex;

    @Size(max=100, message = "se permite un máximo de 100 caracteres")
    @Column(name = "gsm")
    private String gsm;

    @Size(max=100, message = "se permite un máximo de 100 caracteres")
    @Column(name = "alive")
    private String alive;

    @Positive
    @Digits(integer = 10, fraction = 0)
    @Column(name = "appearances")
    private Integer appearances;

    @Size(max=100, message = "se permite un máximo de 100 caracteres")
    @Column(name = "first_appearance")
    private String firstAppearance;

    @Positive
    @Digits(integer = 10, fraction = 0)
    @Column(name = "year")
    private Integer year;

}
