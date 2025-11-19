package com.GreenCart.GreenCart.domain.service;


import com.GreenCart.GreenCart.domain.Order;
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
    private ProductoCrudRepository productoRepository;

    @Autowired
    private OrderRepository orderRepository;

    //Obtener todas las órdenes
    public List<Order> getAll() {
        return orderRepository.getAll();
    }

    //Obtener una orden específica
    public Optional<Order> getOrder(Integer orderId) {
        return orderRepository.getOrder(orderId);
    }

    //Obtener todas las órdenes de un comprador específico
    public List<Order> getByBuyer(Integer buyerId) {
        return orderRepository.getByBuyer(buyerId);
    }

    //Guardar o actualizar una orden
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    //Eliminar una orden, dudo mucho que lo usemos
    public boolean delete(Integer orderId) {
        return getOrder(orderId)
                .map(order -> {
                    orderRepository.delete(orderId);
                    return true;
                })
                .orElse(false);
    }

    //busca TODAS las órdenes, pero filtra solo los items del vendedor
    public List<Order> getOrdersBySeller(Integer sellerId) {
        List<Order> allOrders = orderRepository.getAll();

        return allOrders.stream()
                .map(order -> {

                    // Filtrar SOLO los items del vendedor
                    var filteredItems = order.getItems().stream()
                            .filter(item -> item.getSellerId() == sellerId)
                            .toList();

                    if (filteredItems.isEmpty()) {
                        return null; // esta orden NO pertenece al vendedor
                    }

                    // Remplazar los items por los filtrados
                    order.setItems(filteredItems);

                    return order;
                })
                .filter(o -> o != null)
                .toList();
    }
}
