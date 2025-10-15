
package com.GreenCart.GreenCart.domain.repository;

import com.GreenCart.GreenCart.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> getAll();
    Optional<User> getUser(int userId);
    User save(User user);
    void delete(int userId);
}