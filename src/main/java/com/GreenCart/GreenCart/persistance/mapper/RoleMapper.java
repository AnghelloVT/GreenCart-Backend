package com.GreenCart.GreenCart.persistance.mapper;

import com.GreenCart.GreenCart.domain.Role;
import com.GreenCart.GreenCart.persistance.entity.Rol;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mappings({
        @Mapping(source = "idRol", target = "idRole"),
        @Mapping(source = "nombre", target = "name")
    })
    Role toRole(Rol rol);

    @InheritInverseConfiguration
    Rol toRol(Role role);
}