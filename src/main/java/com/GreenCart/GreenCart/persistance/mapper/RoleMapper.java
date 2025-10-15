
package com.GreenCart.GreenCart.persistance.mapper;
import com.GreenCart.GreenCart.domain.Role;
import com.GreenCart.GreenCart.persistance.entity.Rol;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mappings({
        @Mapping(source = "idRol", target = "id"),
        @Mapping(source = "nombre", target = "nombre")
    })
    Role toRole(Rol rol);

    List<Role> toRoles(List<Rol> roles);

    @InheritInverseConfiguration
    Rol toRol(Role role);
}