package com.curso.ecomerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Orden {

    private Integer id;
    private String numero;
    private Date fechaCreacion;
    private Date fechaRecivida;
    private double total;



}
