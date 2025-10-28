package com.GreenCart.GreenCart.domain.service;

import com.GreenCart.GreenCart.domain.OrderItem;
import com.GreenCart.GreenCart.domain.repository.OrderItemRepository;
import com.GreenCart.GreenCart.persistance.crud.ProductoCrudRepository;
import com.GreenCart.GreenCart.persistance.crud.UsuarioCrudRepository;
import com.GreenCart.GreenCart.persistance.entity.Producto;
import com.GreenCart.GreenCart.persistance.entity.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UsuarioCrudRepository usuarioRepository;

    @Autowired
    private ProductoCrudRepository productoRepository;

    // Listar todos los items
    public List<OrderItem> getAll() {
        return orderItemRepository.getAll();
    }

    // Obtener un item por ID
    public Optional<OrderItem> getItem(Integer itemId) {
        return orderItemRepository.getOrderItem(itemId);
    }

    // Obtener items por pedido
    public List<OrderItem> getByOrder(Integer orderId) {
        return orderItemRepository.getByOrder(orderId);
    }

    // Obtener items por vendedor
    public List<OrderItem> getBySeller(Integer sellerId) {
        return orderItemRepository.getBySeller(sellerId);
    }

    // Guardar un item
    public OrderItem save(OrderItem orderItem) {
        // Validar IDs
        if (orderItem.getOrderId() == null) {
            throw new IllegalArgumentException("El ID del pedido no puede ser null");
        }
        if (orderItem.getProductId() == null) {
            throw new IllegalArgumentException("El ID del producto no puede ser null");
        }

        // Traer el producto desde la DB
        Producto producto = productoRepository.findById(orderItem.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + orderItem.getProductId()));

        // Asignar vendedor automáticamente desde el producto
        if (producto.getVendedor() == null) {
            throw new IllegalArgumentException("El producto no tiene vendedor asignado");
        }
        orderItem.setSellerId(producto.getVendedor().getId().intValue());

        return orderItemRepository.save(orderItem);
    }

    // Eliminar un item (opcional)
    public boolean delete(Integer itemId) {
        return getItem(itemId)
                .map(item -> {
                    orderItemRepository.delete(itemId);
                    return true;
                })
                .orElse(false);
    }
}
