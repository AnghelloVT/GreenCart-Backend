package com.GreenCart.GreenCart.persistance.mapper;
import com.GreenCart.GreenCart.domain.User;
import com.GreenCart.GreenCart.persistance.entity.Usuario;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "nombre", target = "firstName"),
        @Mapping(source = "apellidos", target = "lastName"),
        @Mapping(source = "correo", target = "email"),
        @Mapping(source = "direccion", target = "address"),
        @Mapping(source = "telefono", target = "phone"),
        @Mapping(source = "contraseña", target = "password"),
        @Mapping(source = "rol", target = "roles")
    })
    User toUser(Usuario usuario);

    @InheritInverseConfiguration
    Usuario toUsuario(User user);
}