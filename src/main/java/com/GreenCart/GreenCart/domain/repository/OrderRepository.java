package com.GreenCart.GreenCart.domain.repository;

import com.GreenCart.GreenCart.domain.Order;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<Order> getAll();
    Optional<Order> getOrder(Integer orderId);
    List<Order> getByBuyer(Integer buyerId);
    Order save(Order order);
    void delete(Integer orderId);
}
