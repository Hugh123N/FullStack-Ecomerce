package com.curso.ecomerce.repository;

import com.curso.ecomerce.model.Orden;
import com.curso.ecomerce.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Integer> {
    //se puede hacer cualquier filtro
    List<Orden> findByUsuario(Usuario usuario);

}
