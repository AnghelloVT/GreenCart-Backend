
package com.GreenCart.GreenCart.persistance.mapper;

import com.GreenCart.GreenCart.domain.User;
import com.GreenCart.GreenCart.persistance.entity.Usuario;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {

    @Mappings({
        @Mapping(source = "idUsuario", target = "id"),
        @Mapping(source = "nombre", target = "nombre"),
        @Mapping(source = "apellido", target = "apellido"),
        @Mapping(source = "correo", target = "correo"),
        @Mapping(source = "roles", target = "roles") // List<Rol> → List<String> via RoleMapper
    })
    User toUser(Usuario usuario);

    List<User> toUsers(List<Usuario> usuarios);

    @InheritInverseConfiguration
    @Mapping(target = "contraseña", ignore = true)
    @Mapping(target = "direccion", ignore = true)
    @Mapping(target = "telefono", ignore = true)
    @Mapping(target = "dni", ignore = true)
    Usuario toUsuario(User user);
}