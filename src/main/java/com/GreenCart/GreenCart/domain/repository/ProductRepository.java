package com.GreenCart.GreenCart.domain.repository;

import com.GreenCart.GreenCart.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> getAll();

    Optional<Product> getProduct(int productId);

    Optional<List<Product>> getByCategory(int categoryId);

    List<Product> getByVendedor(Long vendedorId);

    Product save(Product product);

    void delete(int productId);

    Product update(Product product);

    Product updateProductStock(Product product);
}
