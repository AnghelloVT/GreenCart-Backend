package com.GreenCart.GreenCart.persistance;

import com.GreenCart.GreenCart.domain.User;
import com.GreenCart.GreenCart.domain.repository.UserRepository;
import com.GreenCart.GreenCart.persistance.entity.Usuario;
import com.GreenCart.GreenCart.persistance.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepository {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;

    public User save(User user) {
        Usuario usuario = mapper.toUsuario(user);
        Usuario saved = userRepository.save(usuario);
        return mapper.toUser(saved);
    }

    public Optional<User> findByCorreo(String correo) {
        return userRepository.findByCorreo(correo)
                .map(mapper::toUser); 
    }
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id)
                .map(mapper::toUser);
    }

    public List<Usuario> findAllUsuarios() {
        return userRepository.findAll();
    }

    public Optional<Usuario> findUsuarioById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User update(User user) {
        Usuario entity = mapper.toUsuario(user);
        Usuario updated = userRepository.save(entity);
        return mapper.toUser(updated);
    }

    public boolean existsByCorreo(String correo) {
        return userRepository.existsByCorreo(correo);
    }

    public Optional<Usuario> findUsuarioByCorreo(String correo) {
    return userRepository.findByCorreo(correo); 
}
}
