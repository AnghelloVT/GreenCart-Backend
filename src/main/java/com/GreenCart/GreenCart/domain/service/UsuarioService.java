package com.GreenCart.GreenCart.domain.service;

import com.GreenCart.GreenCart.domain.User;
import com.GreenCart.GreenCart.persistance.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public boolean existeCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).isPresent();
    }

    public User registrarUsuario(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usuarioRepository.save(user);
    }

    public boolean validarCredenciales(String correo, String contrasenia) {
        return usuarioRepository.findByCorreo(correo)
                .map(user -> passwordEncoder.matches(contrasenia, user.getPassword()))
                .orElse(false);
    }
}
