package com.curso.ecomerce.service;

import com.curso.ecomerce.model.Orden;
import com.curso.ecomerce.model.Usuario;

import java.util.List;

public interface IOrdenService {

    List<Orden> findAll();
    Orden save(Orden orden);

    //metodos adicionales
    String generarNumeroOrden();
    //listar las compras
    List<Orden> findByUsuario(Usuario usuario);

}
