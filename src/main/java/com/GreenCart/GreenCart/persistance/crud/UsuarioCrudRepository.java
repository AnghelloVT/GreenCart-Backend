package com.GreenCart.GreenCart.persistance.crud;

import com.GreenCart.GreenCart.persistance.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioCrudRepository extends CrudRepository<Usuario, Integer> {
    
}
