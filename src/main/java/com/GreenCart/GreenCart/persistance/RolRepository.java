package com.GreenCart.GreenCart.persistance;

import com.GreenCart.GreenCart.domain.Role;
import com.GreenCart.GreenCart.domain.repository.RoleRepository;
import com.GreenCart.GreenCart.persistance.crud.RolCrudRepository;
import com.GreenCart.GreenCart.persistance.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RolRepository implements RoleRepository {

    @Autowired
    private RolCrudRepository rolCrudRepository;

    @Autowired
    private RoleMapper mapper;

    @Override
    public Optional<Role> findByRoleName(String roleName) {
        return rolCrudRepository.findByNombre(roleName)
                .map(rol -> mapper.toRole(rol));
    }
}