package com.curso.ecomerce.service;

import com.curso.ecomerce.model.Producto;
import com.curso.ecomerce.repository.IProductoRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Service
public class ProductoServiceImpl implements ProductoService{
    @Autowired
    private IProductoRepository repository;

    @Override
    public List<Producto> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Producto> get(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Producto save(Producto producto) {
        return repository.save(producto);
    }

    @Override
    public void update(Producto producto) {
        repository.save(producto);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
