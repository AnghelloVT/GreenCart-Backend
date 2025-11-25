package com.GreenCart.GreenCart.web.controller;

import com.GreenCart.GreenCart.domain.User;
import com.GreenCart.GreenCart.domain.service.UsuarioService;
import com.GreenCart.GreenCart.persistance.entity.Rol;
import com.GreenCart.GreenCart.persistance.entity.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Map<String, Object>>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodos();

        List<Map<String, Object>> response = usuarios.stream().map(usuario -> {
            Map<String, Object> u = new HashMap<>();
            u.put("id", usuario.getId());
            u.put("nombre", usuario.getNombre());
            u.put("apellidos", usuario.getApellidos());
            u.put("correo", usuario.getCorreo());
            u.put("dni", usuario.getDni());
            u.put("direccion", usuario.getDireccion());
            u.put("telefono", usuario.getTelefono());
            u.put("roles", usuario.getRol()
                    .stream()
                    .map(r -> r.getNombre())
                    .collect(Collectors.toList()));
            return u;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Long id) {
        User u = usuarioService.getUserById(id);

        if (u == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Usuario no encontrado"));

        return ResponseEntity.ok(u);
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<?> obtenerPorCorreo(@PathVariable String correo) {
        User user = usuarioService.getUserByCorreo(correo);

        if (user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Usuario no encontrado"));

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {

        User user = usuarioService.getUserById(id);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "El usuario no existe"));
        }

        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.ok(Map.of("message", "Usuario eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "No se pudo eliminar el usuario", "details", e.getMessage()));
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Map<String, Object> body) {

        User user = usuarioService.getUserById(id);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Usuario no encontrado"));
        }

        try {
            user.setFirstName((String) body.get("nombre"));
            user.setLastName((String) body.get("apellidos"));
            user.setEmail((String) body.get("correo"));
            user.setDni((String) body.get("dni"));
            user.setPhone((String) body.get("telefono"));

            usuarioService.editarUsuario(user);

            return ResponseEntity.ok(Map.of("message", "Usuario actualizado correctamente"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al actualizar usuario", "details", e.getMessage()));
        }
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
                    .map(r -> r.getNombre().toLowerCase()) // "administrador", "vendedor", "comprador"
                    .orElse("comprador");
            return ResponseEntity.ok(Map.of(
                    "status", "ok",
                    "rol", rol,
                    "id", usuario.getId().toString(),
                    "nombre", usuario.getNombre(),
                    "apellidos", usuario.getApellidos(),
                    "correo", usuario.getCorreo(),
                    "dni", usuario.getDni(),
                    "direccion", usuario.getDireccion(),
                    "telefono", usuario.getTelefono()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("status", "error"));
        }
    }
}
