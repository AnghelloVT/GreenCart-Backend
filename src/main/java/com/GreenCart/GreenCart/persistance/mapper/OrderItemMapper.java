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

    // Entity -> DTO
    @Mapping(target = "itemId", source = "idItem")
    @Mapping(target = "orderId", source = "pedido.idPedido")
    @Mapping(target = "productId", source = "producto.idProducto")
    @Mapping(target = "sellerId", source = "usuario.id")
    @Mapping(target = "quantity", source = "cantidad")
    @Mapping(target = "unitPrice", source = "precioUnitario")
    @Mapping(target = "total", source = "total")
    @Mapping(target = "status", source = "estado")
    OrderItem toOrderItem(Pedido_Item pedidoItem);

    List<OrderItem> toOrderItems(List<Pedido_Item> items);

    // DTO -> Entity
    @Mapping(target = "idItem", source = "itemId")
    @Mapping(target = "cantidad", source = "quantity")
    @Mapping(target = "precioUnitario", source = "unitPrice")
    @Mapping(target = "total", source = "total")
    @Mapping(target = "estado", source = "status")
    @Mapping(target = "pedido", ignore = true)   // se asigna manualmente en repository
    @Mapping(target = "producto", ignore = true) // se asigna manualmente en repository
    @Mapping(target = "usuario", ignore = true)  // se asigna manualmente en repository
    Pedido_Item toPedidoItem(OrderItem orderItem);
}
