
package com.GreenCart.GreenCart.persistance;

import com.GreenCart.GreenCart.domain.User;
import com.GreenCart.GreenCart.domain.repository.UserRepository;
import com.GreenCart.GreenCart.persistance.crud.UsuarioCrudRepository;
import com.GreenCart.GreenCart.persistance.entity.Usuario;
import com.GreenCart.GreenCart.persistance.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepository implements UserRepository {

    @Autowired
    private UsuarioCrudRepository usuarioCrudRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAll() {
        return userMapper.toUsers((List<Usuario>) usuarioCrudRepository.findAll());
    }

    @Override
    public Optional<User> getUser(int userId) {
        return usuarioCrudRepository.findById(userId).map(userMapper::toUser);
    }

    @Override
    public User save(User user) {
        Usuario usuario = userMapper.toUsuario(user);
        return userMapper.toUser(usuarioCrudRepository.save(usuario));
    }

    @Override
    public void delete(int userId) {
        usuarioCrudRepository.deleteById(userId);
    }
}
