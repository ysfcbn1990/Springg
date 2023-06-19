package com.tpe.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("bookName")//JSON formatında name fieldını bu key ile göster
    private String name;

//1 Studentın--->many-book
    @JsonIgnore//book objemi JSON a maplerken studentı alma
    @ManyToOne
    private Student student;//one


}
