package com.curso.ecomerce.repository;

import com.curso.ecomerce.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Integer> {
}
