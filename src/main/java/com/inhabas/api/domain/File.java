package com.inhabas.api.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class File {

    @Id
    @GeneratedValue
    private Integer Id;
}
