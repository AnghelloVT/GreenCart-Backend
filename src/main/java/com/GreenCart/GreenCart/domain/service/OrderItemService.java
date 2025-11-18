package com.GreenCart.GreenCart.domain.service;

import com.GreenCart.GreenCart.domain.OrderItem;
import com.GreenCart.GreenCart.domain.repository.OrderItemRepository;
import com.GreenCart.GreenCart.persistance.crud.PedidoCrudRepository;
import com.GreenCart.GreenCart.persistance.crud.PedidoItemCrudRepository;
import com.GreenCart.GreenCart.persistance.crud.ProductoCrudRepository;
import com.GreenCart.GreenCart.persistance.crud.UsuarioCrudRepository;
import com.GreenCart.GreenCart.persistance.entity.Pedido;
import com.GreenCart.GreenCart.persistance.entity.Pedido_Item;
import com.GreenCart.GreenCart.persistance.entity.Producto;
import com.GreenCart.GreenCart.persistance.entity.Usuario;

import com.GreenCart.GreenCart.persistance.mapper.OrderItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private PedidoCrudRepository pedidoRepository;

    @Autowired
    private UsuarioCrudRepository usuarioRepository;

    @Autowired
    private ProductoCrudRepository productoRepository;

    @Autowired
    private OrderItemMapper mapper;

    @Autowired
    private PedidoItemCrudRepository pedidoItemRepository;

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
        // Convertir DTO a entidad
        Pedido_Item entity = mapper.toPedidoItem(orderItem);

        // Buscar entidades reales
        Pedido pedido = pedidoRepository.findById(orderItem.getOrderId())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        Producto producto = productoRepository.findById(orderItem.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Usuario usuario = usuarioRepository.findById(Long.valueOf(orderItem.getSellerId()))
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));

        // Asignar relaciones
        entity.setPedido(pedido);
        entity.setProducto(producto);
        entity.setUsuario(usuario);
        System.out.println("Guardando item con datos: "
                + entity.getCantidad() + ", "
                + entity.getPrecioUnitario() + ", "
                + entity.getTotal() + ", Pedido: "
                + entity.getPedido().getIdPedido() + ", Producto: "
                + entity.getProducto().getIdProducto() + ", Usuario: "
                + entity.getUsuario().getId());
        // Guardar
        Pedido_Item saved = pedidoItemRepository.save(entity);

        // Convertir de nuevo a DTO para devolver
        return mapper.toOrderItem(saved);
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

    public boolean updateStatus(int itemId, String status) {
        return orderItemRepository.updateStatus(itemId, status);
    }
}
