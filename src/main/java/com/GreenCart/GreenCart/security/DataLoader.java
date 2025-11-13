package com.GreenCart.GreenCart.security;

import com.GreenCart.GreenCart.persistance.crud.RolCrudRepository;
import com.GreenCart.GreenCart.persistance.crud.UsuarioCrudRepository;
import com.GreenCart.GreenCart.persistance.entity.Rol;
import com.GreenCart.GreenCart.persistance.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class DataLoader {
    @Autowired
    private UsuarioCrudRepository usuarioRepository;

    @Autowired
    private RolCrudRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        Rol adminRol = rolRepository.findByNombre("ADMIN")
                .orElseGet(() -> {
                    Rol rol = new Rol();
                    rol.setNombre("ADMIN");
                    return rolRepository.save(rol);
                });

        Optional<Usuario> existingAdmin = usuarioRepository.findByCorreo("admin@admin.com");
        if (existingAdmin.isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNombre("Admin");
            admin.setApellidos("Principal");
            admin.setCorreo("admin@admin.com");
            admin.setContraseña(passwordEncoder.encode("admin"));
            admin.setDni("00000000");
            admin.setTelefono("000000000");
            admin.setDireccion("Administración");

            Set<Rol> roles = new HashSet<>();
            roles.add(adminRol);
            admin.setRol(roles);

            usuarioRepository.save(admin);
        }
    }
}
