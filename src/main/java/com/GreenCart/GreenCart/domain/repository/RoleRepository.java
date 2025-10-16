package com.GreenCart.GreenCart.domain.repository;

import com.GreenCart.GreenCart.domain.Role;
import java.util.Optional;

public interface RoleRepository {

    Optional<Role> findByRoleName(String nombre);
}
