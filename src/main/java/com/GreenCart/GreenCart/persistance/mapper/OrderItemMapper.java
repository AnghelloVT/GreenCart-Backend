package com.GreenCart.GreenCart.persistance.mapper;

import com.GreenCart.GreenCart.domain.OrderItem;
import com.GreenCart.GreenCart.persistance.entity.Pedido_Item;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mappings({
            @Mapping(source = "idItem", target = "itemId"),
            @Mapping(source = "pedido.idPedido", target = "orderId"),
            @Mapping(source = "producto.idProducto", target = "productId"),
            @Mapping(source = "usuario.id", target = "sellerId"),
            @Mapping(source = "cantidad", target = "quantity"),
            @Mapping(source = "precioUnitario", target = "unitPrice"),
            @Mapping(source = "total", target = "total"),
            @Mapping(source = "estado", target = "status")
    })
    OrderItem toOrderItem(Pedido_Item pedidoItem);

    List<OrderItem> toOrderItems(List<Pedido_Item> items);

    @InheritInverseConfiguration
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "producto", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    Pedido_Item toPedidoItem(OrderItem orderItem);

}
