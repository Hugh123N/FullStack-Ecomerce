package com.curso.ecomerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ordenes")
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String numero;
    private Date fechaCreacion;
    private Date fechaRecivida;
    private double total;
    //muchos ordenes puede tener un Usuario
    @ManyToOne
    private Usuario usuario;
    //orden ft detalleOrden
    @OneToMany(mappedBy = "orden")
    private List<DetalleOrden> detalle;


}
