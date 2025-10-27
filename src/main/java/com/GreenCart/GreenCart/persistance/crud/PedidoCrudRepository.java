package com.GreenCart.GreenCart.persistance.crud;

import com.GreenCart.GreenCart.persistance.entity.Pedido;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface PedidoCrudRepository extends CrudRepository<Pedido, Integer>{
    List<Pedido> findByUsuario_Id(Integer idUsuario);
}
