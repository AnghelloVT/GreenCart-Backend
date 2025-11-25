package com.GreenCart.GreenCart.domain.service;

import com.GreenCart.GreenCart.domain.User;
import com.GreenCart.GreenCart.domain.repository.RoleRepository;
import com.GreenCart.GreenCart.persistance.UsuarioRepository;
import com.GreenCart.GreenCart.domain.repository.UserRepository;
import com.GreenCart.GreenCart.persistance.entity.Usuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RoleRepository roleRepository) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public boolean existeCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).isPresent();
    }

    public void asignarRol(User usuario, String nombreRol) {
        roleRepository.findByRoleName(nombreRol)
                .ifPresent(rol -> usuario.setRoles(Collections.singleton(rol)));
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

    public Usuario obtenerEntidadPorCorreo(String correo) {
    return usuarioRepository.findUsuarioByCorreo(correo).orElse(null);
    }
    public User getUserById(Long id) {
        return usuarioRepository.findUserById(id).orElse(null);
    }

    public User getUserByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).orElse(null);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAllUsuarios();
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public User editarUsuario(User userEditado) {

        User original = usuarioRepository.findUserById(userEditado.getId())
                .orElse(null);
        if (original == null) return null;

        original.setFirstName(userEditado.getFirstName());
        original.setLastName(userEditado.getLastName());
        original.setEmail(userEditado.getEmail());
        original.setPhone(userEditado.getPhone());
        original.setAddress(userEditado.getAddress());
        original.setDni(userEditado.getDni());

        return usuarioRepository.update(original);
    }
}
