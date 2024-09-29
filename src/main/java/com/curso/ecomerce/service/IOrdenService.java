package com.curso.ecomerce.service;

import com.curso.ecomerce.model.Orden;
import com.curso.ecomerce.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IOrdenService {

    List<Orden> findAll();
    Optional<Orden> findById(Integer id);
    Orden save(Orden orden);

    //metodos adicionales
    String generarNumeroOrden();
    //listar las compras
    List<Orden> findByUsuario(Usuario usuario);

}
