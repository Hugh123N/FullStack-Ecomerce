package com.curso.ecomerce.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleOrden {

    private Integer id;
    private String nombre;
    private double cantidad;
    private double precio;
    private double total;


}
