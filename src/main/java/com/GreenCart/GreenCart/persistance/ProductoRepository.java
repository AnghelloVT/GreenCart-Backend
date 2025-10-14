package com.GreenCart.GreenCart.persistance;

import com.GreenCart.GreenCart.domain.Product;
import com.GreenCart.GreenCart.domain.repository.ProductRepository;
import com.GreenCart.GreenCart.persistance.crud.ProductoCrudRepository;
import com.GreenCart.GreenCart.persistance.entity.Producto;
import com.GreenCart.GreenCart.persistance.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepository implements ProductRepository {
    @Autowired
    private ProductoCrudRepository productoCrudRepository;

    @Autowired
    private ProductMapper productMapper;

    //Funcion Actualizada
    @Override
    public List<Product> getAll(){
        List <Producto> productos  = (List<Producto>) productoCrudRepository.findAll();
        return  productMapper.toProducts(productos);

    }
    //Funcion Actualizada
    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List <Producto> productos = productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId);
        return Optional.of( productMapper.toProducts(productos));
    }

    //Funcion Actualizada
    @Override
    public Optional<List<Product>> getScarseProducts(int quantity) {
        Optional<List<Producto>>  productos = productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity, true);
        return  productos.map(prods -> productMapper.toProducts(prods));
    }
    //Funcion Actualizada
    @Override
    public Optional<Product> getProduct(int productId) {
        return productoCrudRepository.findById(productId).map(producto ->productMapper.toProduct(producto));
    }

    //Funcion Actualizada
    @Override
    public Product save(Product product) {
        Producto producto = productMapper.toProducto(product);
        return productMapper.toProduct(productoCrudRepository.save(producto));
    }

    public void delete(int idProducto) {
        productoCrudRepository.deleteById(idProducto);
    }

}
