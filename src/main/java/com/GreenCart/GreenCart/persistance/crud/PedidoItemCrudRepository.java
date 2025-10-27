    package com.GreenCart.GreenCart.persistance.crud;

    import com.GreenCart.GreenCart.persistance.entity.Pedido_Item;
    import org.springframework.data.repository.CrudRepository;
    import java.util.List;

    public interface PedidoItemCrudRepository extends CrudRepository<Pedido_Item, Integer> {
        List<Pedido_Item> findByPedidoIdPedido(Integer idPedido);
        List<Pedido_Item> findByUsuario_Id(Integer idUsuario);
    }
