package com.GreenCart.GreenCart.persistance.mapper;

import com.GreenCart.GreenCart.domain.Product;
import com.GreenCart.GreenCart.persistance.entity.Producto;

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
            @Mapping(source = "categoria", target = "productCategory"),
            @Mapping(source = "vendedor.id", target = "vendedorId"),
            @Mapping(source = "imagenproducto", target = "productImage")
            

    })
    Product toProduct(Producto producto);

    List<Product> toProducts(List<Producto>productos);

    
     @Mappings({
            @Mapping(target = "idProducto", ignore = true), 
            @Mapping(source = "productName", target = "nombre"),
            @Mapping(source = "categoryId", target = "idCategoria"),
            @Mapping(source = "productDescription", target = "descripcionproducto"),
            @Mapping(source = "productPrice", target = "preciounitario"),
            @Mapping(source = "productStock", target = "cantidadStock"),
            @Mapping(source = "active", target = "estado"),
            @Mapping(source = "productImage", target = "imagenproducto"),
            @Mapping(target = "categoria", ignore = true),
             @Mapping(source = "vendedorId", target = "vendedor.id")
             
    })
    Producto toProductoCreate(Product product);

     @Mappings({
          @Mapping(source = "productId", target = "idProducto"),
    @Mapping(source = "productName", target = "nombre"),
    @Mapping(source = "categoryId", target = "idCategoria"),
    @Mapping(source = "productDescription", target = "descripcionproducto"),
    @Mapping(source = "productPrice", target = "preciounitario"),
    @Mapping(source = "productStock", target = "cantidadStock"),
    @Mapping(source = "active", target = "estado"),
    @Mapping(source = "productImage", target = "imagenproducto"),
    @Mapping(target = "categoria", ignore = true),
    @Mapping(source = "vendedorId", target = "vendedor.id")
    })
    Producto toProductoUpdate(Product product);
}
