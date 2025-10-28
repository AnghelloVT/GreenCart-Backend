package com.GreenCart.GreenCart.persistance.mapper;

import com.GreenCart.GreenCart.domain.Order;
import com.GreenCart.GreenCart.persistance.entity.Pedido;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {
    @Mappings({
            @Mapping(source = "idPedido", target = "orderId"),
            @Mapping(source = "fecha", target = "date"),
            @Mapping(source = "estado", target = "status"),
            @Mapping(source = "total", target = "total"),
            @Mapping(source = "usuario.id", target = "buyerId"),
            @Mapping(source = "items", target = "items")
    })
    Order toOrder(Pedido pedido);

    List<Order> toOrders(List<Pedido> pedidos);

    @InheritInverseConfiguration
    @Mapping(target = "usuario", ignore = true)
    Pedido toPedido(Order order);
}
