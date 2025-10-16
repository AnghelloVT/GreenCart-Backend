package com.GreenCart.GreenCart.persistance.crud;

import com.GreenCart.GreenCart.persistance.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RolCrudRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByNombre(String nombre);
}
