package com.GreenCart.GreenCart.persistance.crud;

import com.GreenCart.GreenCart.persistance.entity.Categoria;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CategoriaCrudRepository extends CrudRepository<Categoria, Integer> {
    List<Categoria> findByEstadoTrue();
}