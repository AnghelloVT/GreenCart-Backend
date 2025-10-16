package com.GreenCart.GreenCart.persistance.crud;

import com.GreenCart.GreenCart.persistance.entity.Usuario;
<<<<<<< HEAD
import org.springframework.data.repository.CrudRepository;

public interface UsuarioCrudRepository extends CrudRepository<Usuario, Integer> {
    
=======
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioCrudRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreo(String correo);

    boolean existsByCorreo(String correo);
>>>>>>> 56c4c974170d3e4cc7e2bf6693dcc2e256b7fc05
}
