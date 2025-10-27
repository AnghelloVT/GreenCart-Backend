package com.GreenCart.GreenCart.domain.repository;

import com.GreenCart.GreenCart.domain.OrderItem;
import java.util.List;
import java.util.Optional;

public interface OrderItemRepository {
    List<OrderItem> getAll();
    Optional<OrderItem> getOrderItem(Integer orderItemId);
    List<OrderItem> getByOrder(Integer orderId);
    List<OrderItem> getBySeller(Integer sellerId);
    OrderItem save(OrderItem orderItem);
    void delete(Integer orderItemId);
}
