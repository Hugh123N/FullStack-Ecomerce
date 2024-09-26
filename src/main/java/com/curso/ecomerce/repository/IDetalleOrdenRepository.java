package com.curso.ecomerce.repository;

import com.curso.ecomerce.model.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDetalleOrdenRepository extends JpaRepository<DetalleOrden,Integer> {
}
