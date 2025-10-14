package com.GreenCart.GreenCart.persistance.mapper;

import com.GreenCart.GreenCart.domain.Product;
import com.GreenCart.GreenCart.persistance.entity.Producto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {
    @Mappings({
            @Mapping(source = "idProducto", target = "productId"),
            @Mapping(source = "nombre", target = "productName"),
            @Mapping(source = "idCategoria", target = "categoryId"),
            @Mapping(source = "descripcionproducto", target = "productDescription"),
            @Mapping(source = "preciounitario", target = "productPrice"),
            @Mapping(source = "cantidadStock", target = "productStock"),
            @Mapping(source = "estado", target = "active"),
            @Mapping(source = "categoria", target = "productCategory")

    })
    Product toProduct(Producto producto);

    List<Product> toProducts(List<Producto>productos);

    @InheritInverseConfiguration
    @Mapping(target = "imagen", ignore = true)
    Producto toProducto(Product product);
}
