package com.curso.ecomerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String username;
    private String email;
    private String direccion;
    private String telefono;
    private String tipo;
    private String password;
    //usuario lleva muchos productos=>mapea con el atributo usuario que esta en la T producto
    @OneToMany(mappedBy = "usuario")
    private List<Producto> productos;
    //un Usuario puede tener muchos ordenes =>mapeo por el campo usuario en la T Orden
    @OneToMany(mappedBy = "usuario")
    private List<Orden> ordenes;
}
