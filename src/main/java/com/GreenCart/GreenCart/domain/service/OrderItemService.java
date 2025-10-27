package com.GreenCart.GreenCart.domain.service;

import com.GreenCart.GreenCart.domain.OrderItem;
import com.GreenCart.GreenCart.domain.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    //Listar todos los items
    public List<OrderItem> getAll() {
        return orderItemRepository.getAll();
    }

    //Obtener un item por ID
    public Optional<OrderItem> getItem(Integer itemId) {
        return orderItemRepository.getOrderItem(itemId);
    }

    //Obtener items por pedido
    public List<OrderItem> getByOrder(Integer orderId) {
        return orderItemRepository.getByOrder(orderId);
    }

    //Obtener items por vendedor
    public List<OrderItem> getBySeller(Integer sellerId) {
        // Este metodo se agregará también en OrderItemRepository (si aún no lo tienes)
        return orderItemRepository.getBySeller(sellerId);
    }

    //Guardar un item
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    //Eliminar un item, dudo mucho que lo usemos
    public boolean delete(Integer itemId) {
        return getItem(itemId)
                .map(item -> {
                    orderItemRepository.delete(itemId);
                    return true;
                })
                .orElse(false);
    }
}
