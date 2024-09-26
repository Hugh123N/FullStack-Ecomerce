package com.curso.ecomerce.service;

import com.curso.ecomerce.model.Orden;

import java.util.List;

public interface IOrdenService {

    List<Orden> findAll();
    Orden save(Orden orden);


}
