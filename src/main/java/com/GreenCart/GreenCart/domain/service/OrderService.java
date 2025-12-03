package com.GreenCart.GreenCart.domain.service;


import com.GreenCart.GreenCart.domain.Order;
import com.GreenCart.GreenCart.domain.OrderItem;
import com.GreenCart.GreenCart.domain.repository.OrderItemRepository;
import com.GreenCart.GreenCart.domain.repository.OrderRepository;
import com.GreenCart.GreenCart.persistance.crud.ProductoCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class OrderService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    // Obtener todas las órdenes
    public List<Order> getAll() {
        return orderRepository.getAll();
    }

    // Obtener una orden específica
    public Optional<Order> getOrder(Integer orderId) {
        return orderRepository.getOrder(orderId);
    }

    // Obtener todas las órdenes de un comprador específico
    public List<Order> getByBuyer(Integer buyerId) {
        return orderRepository.getByBuyer(buyerId);
    }

    // Guardar o actualizar una orden y sus items
    public Order save(Order order) {
        // 1️⃣ Guardar el pedido primero
        Order savedOrder = orderRepository.save(order);

        // 2️⃣ Guardar cada item, asignando el orderId
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            for (OrderItem item : order.getItems()) {
                item.setOrderId(savedOrder.getOrderId()); // asignar ID del pedido
                orderItemRepository.save(item);
            }
        }

        // 3️⃣ Traer el pedido actualizado con items
        return getOrder(savedOrder.getOrderId()).orElse(savedOrder);
    }

    // Eliminar una orden
    public boolean delete(Integer orderId) {
        return getOrder(orderId)
            .map(order -> {
                orderRepository.delete(orderId);
                return true;
            })
            .orElse(false);
    }

    // Obtener órdenes por vendedor
    public List<Order> getOrdersBySeller(Integer sellerId) {
        List<Order> allOrders = orderRepository.getAll();
        return allOrders.stream()
            .map(order -> {
                var filteredItems = order.getItems().stream()
                    .filter(item -> item.getSellerId() == sellerId)
                    .toList();
                if (filteredItems.isEmpty()) return null;
                order.setItems(filteredItems);
                return order;
            })
            .filter(o -> o != null)
            .toList();
    }
}
