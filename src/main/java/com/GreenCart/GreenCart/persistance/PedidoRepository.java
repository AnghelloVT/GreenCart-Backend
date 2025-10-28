package com.GreenCart.GreenCart.persistance;

import com.GreenCart.GreenCart.domain.Order;
import com.GreenCart.GreenCart.domain.repository.OrderRepository;
import com.GreenCart.GreenCart.persistance.crud.PedidoCrudRepository;
import com.GreenCart.GreenCart.persistance.entity.Pedido;
import com.GreenCart.GreenCart.persistance.entity.Usuario;
import com.GreenCart.GreenCart.persistance.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class PedidoRepository implements OrderRepository{

    @Autowired
    private PedidoCrudRepository pedidoCrudRepository;

    @Autowired
    private OrderMapper mapper;

    @Override
    public List<Order> getAll() {
        return mapper.toOrders((List<Pedido>) pedidoCrudRepository.findAll());
    }

    @Override
    public Optional<Order> getOrder(Integer orderId) {
        return pedidoCrudRepository.findById(orderId)
                .map(pedido -> mapper.toOrder(pedido));
    }

    @Override
    public List<Order> getByBuyer(Integer buyerId) {
        return mapper.toOrders(pedidoCrudRepository.findByUsuario_Id(buyerId));
    }

    @Override
    public Order save(Order order) {
        Pedido pedido = mapper.toPedido(order);

        // Asignar usuario manualmente
        if (order.getBuyerId() != null) {
            Usuario usuario = new Usuario();
            usuario.setId(Long.valueOf(order.getBuyerId()));
            pedido.setUsuario(usuario);
        }

        Pedido saved = pedidoCrudRepository.save(pedido);
        return mapper.toOrder(saved);
    }

    @Override
    public void delete(Integer orderId) {
        pedidoCrudRepository.deleteById(orderId);
    }
}
