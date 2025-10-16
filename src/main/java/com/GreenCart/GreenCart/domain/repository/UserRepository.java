
package com.GreenCart.GreenCart.domain.repository;

import com.GreenCart.GreenCart.domain.User;
import com.GreenCart.GreenCart.persistance.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {


    Optional<Usuario> findByCorreo(String correo);

    boolean existsByCorreo(String correo);
}

