package com.GreenCart.GreenCart.persistance;

import com.GreenCart.GreenCart.domain.OrderItem;
import com.GreenCart.GreenCart.domain.repository.OrderItemRepository;
import com.GreenCart.GreenCart.persistance.crud.PedidoItemCrudRepository;
import com.GreenCart.GreenCart.persistance.entity.Pedido_Item;
import com.GreenCart.GreenCart.persistance.mapper.OrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class PedidoItemRepository implements OrderItemRepository {
    @Autowired
    private PedidoItemCrudRepository pedidoItemCrudRepository;

    @Autowired
    private OrderItemMapper mapper;

    @Override
    public List<OrderItem> getAll() {
        List<Pedido_Item> items = (List<Pedido_Item>) pedidoItemCrudRepository.findAll();
        return mapper.toOrderItems(items);
    }

    @Override
    public Optional<OrderItem> getOrderItem(Integer orderItemId) {
        return pedidoItemCrudRepository.findById(orderItemId)
                .map(item -> mapper.toOrderItem(item));
    }

    @Override
    public List<OrderItem> getByOrder(Integer orderId) {
        return mapper.toOrderItems(pedidoItemCrudRepository.findByPedidoIdPedido(orderId));
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        Pedido_Item item = mapper.toPedidoItem(orderItem);
        return mapper.toOrderItem(pedidoItemCrudRepository.save(item));
    }

    @Override
    public List<OrderItem> getBySeller(Integer sellerId) {
        return mapper.toOrderItems(pedidoItemCrudRepository.findByUsuario_Id(sellerId));
    }

    @Override
    public void delete(Integer orderItemId) {
        pedidoItemCrudRepository.deleteById(orderItemId);
    }
}
