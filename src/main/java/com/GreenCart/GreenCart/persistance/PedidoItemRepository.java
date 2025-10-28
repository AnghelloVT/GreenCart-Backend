package com.GreenCart.GreenCart.persistance;

import com.GreenCart.GreenCart.domain.OrderItem;
import com.GreenCart.GreenCart.domain.repository.OrderItemRepository;
import com.GreenCart.GreenCart.persistance.crud.PedidoCrudRepository;
import com.GreenCart.GreenCart.persistance.crud.PedidoItemCrudRepository;
import com.GreenCart.GreenCart.persistance.crud.ProductoCrudRepository;
import com.GreenCart.GreenCart.persistance.entity.Pedido;
import com.GreenCart.GreenCart.persistance.entity.Pedido_Item;
import com.GreenCart.GreenCart.persistance.entity.Producto;
import com.GreenCart.GreenCart.persistance.entity.Usuario;
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

    @Autowired
    private PedidoCrudRepository pedidoCrudRepository;

    @Autowired
    private ProductoCrudRepository productoCrudRepository;

    @Override
    public List<OrderItem> getAll() {
        List<Pedido_Item> items = (List<Pedido_Item>) pedidoItemCrudRepository.findAll();
        return mapper.toOrderItems(items);
    }

    @Override
    public Optional<OrderItem> getOrderItem(Integer orderItemId) {
        return pedidoItemCrudRepository.findById(orderItemId)
                .map(mapper::toOrderItem);
    }

    @Override
    public List<OrderItem> getByOrder(Integer orderId) {
        return mapper.toOrderItems(pedidoItemCrudRepository.findByPedidoIdPedido(orderId));
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        Pedido_Item item = mapper.toPedidoItem(orderItem);

        // Traer pedido y producto desde la DB
        Pedido pedido = pedidoCrudRepository.findById(orderItem.getOrderId())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        Producto producto = productoCrudRepository.findById(orderItem.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Asignar automáticamente el vendedor del producto
        Usuario vendedor = producto.getVendedor();
        if (vendedor == null) {
            throw new RuntimeException("El producto no tiene vendedor asignado");
        }

        item.setPedido(pedido);
        item.setProducto(producto);
        item.setUsuario(vendedor); // asigna el vendedor automáticamente

        Pedido_Item savedItem = pedidoItemCrudRepository.save(item);
        return mapper.toOrderItem(savedItem);
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
