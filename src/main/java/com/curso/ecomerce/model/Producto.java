package com.curso.ecomerce.model;


import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String descripcion;
    private String imagen;
    private double precio;
    private int cantidad;
    //relacionamos con la tabla un producto podemos saber de que usuario fue registrado
    //muchos productos lleva un usuario
    @ManyToOne
    private Usuario usuario;
}
