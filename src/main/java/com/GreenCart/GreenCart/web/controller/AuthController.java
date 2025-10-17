package com.GreenCart.GreenCart.web.controller;

import com.GreenCart.GreenCart.domain.User;
import com.GreenCart.GreenCart.domain.service.UsuarioService;
import com.GreenCart.GreenCart.persistance.entity.Rol;
import com.GreenCart.GreenCart.persistance.entity.Usuario;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    public ResponseEntity<String> registro(@RequestParam Map<String, String> body) {
        String correo = body.get("correo");
        if (usuarioService.existeCorreo(correo)) {
            return ResponseEntity.ok("correo_exist");
        }
        
        User usuario = new User();
        usuario.setFirstName(body.get("nombre_cli")); 
        usuario.setLastName(body.get("apellidos_cli"));
        usuario.setEmail(correo);
        usuario.setDni(body.get("dni"));
        usuario.setAddress(body.get("direccion"));
        usuario.setPhone(body.get("telefono"));
        usuario.setPassword(body.get("contraseña"));

        String rolSeleccionado = body.get("rol");

        String nombreRol;
        switch (rolSeleccionado) {
            case "3" -> nombreRol = "ADMINISTRADOR";
            case "2" -> nombreRol = "COMPRADOR";
            case "1" -> nombreRol = "VENDEDOR";
            default -> nombreRol = "COMPRADOR";
        }

        usuarioService.asignarRol(usuario, nombreRol);

        usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/login")
public ResponseEntity<Map<String, String>> login(@RequestParam Map<String, String> body) {
    String correo = body.get("correo");
    String contrasenia = body.get("contraseña");

    if (usuarioService.validarCredenciales(correo, contrasenia)) {
        Usuario usuario = usuarioService.obtenerEntidadPorCorreo(correo);
        if (usuario == null || usuario.getRol().isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("status", "error"));
        }

       
        String rol = usuario.getRol().stream()
                .findFirst()
                .map(r -> r.getNombre().toLowerCase())
                .orElse("comprador");

        return ResponseEntity.ok(Map.of(
            "status", "ok",
            "rol", rol
        ));
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("status", "error"));
    }
}
}
