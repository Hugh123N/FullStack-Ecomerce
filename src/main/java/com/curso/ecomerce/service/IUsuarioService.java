package com.curso.ecomerce.service;

import com.curso.ecomerce.model.Usuario;

import java.util.Optional;

public interface IUsuarioService {
    Optional<Usuario> findById(Integer id);
    Usuario save(Usuario usuario);
}
