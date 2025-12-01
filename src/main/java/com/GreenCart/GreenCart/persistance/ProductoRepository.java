package com.GreenCart.GreenCart.persistance;

import com.GreenCart.GreenCart.domain.Product;
import com.GreenCart.GreenCart.domain.repository.ProductRepository;
import com.GreenCart.GreenCart.persistance.crud.ProductoCrudRepository;
import com.GreenCart.GreenCart.persistance.crud.UsuarioCrudRepository;
import com.GreenCart.GreenCart.persistance.entity.Producto;
import com.GreenCart.GreenCart.persistance.entity.Usuario;
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
    private UsuarioCrudRepository usuarioCrudRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> getAll() {
        List<Producto> productos = (List<Producto>) productoCrudRepository.findAll();
        return productMapper.toProducts(productos);
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List<Producto> productos = productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId);
        return Optional.of(productMapper.toProducts(productos));
    }

    public Optional<List<Product>> getScarseProducts(int quantity) {
        Optional<List<Producto>> productos
                = productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity, true);

        return productos.map(productMapper::toProducts);
    }

    @Override
    public Optional<Product> getProduct(int productId) {
        return productoCrudRepository.findById(productId)
                .map(productMapper::toProduct);
    }

    // SAVE 
    @Override
    public Product save(Product product) {

        Producto entity = productMapper.toProductoCreate(product);

        if (product.getVendedorId() != null) {
            Usuario vendedor = usuarioCrudRepository.findById(product.getVendedorId())
                    .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));
            entity.setVendedor(vendedor);
        }

        entity = productoCrudRepository.save(entity);

        return productMapper.toProduct(entity);
    }

    @Override
    public void delete(int idProducto) {
        productoCrudRepository.deleteById(idProducto);
    }

    //  Buscar por vendedor
    @Override
    public List<Product> getByVendedor(Long vendedorId) {
        List<Producto> productos = productoCrudRepository.findByVendedorId(vendedorId);
        return productMapper.toProducts(productos);
    }

    // UPDATE corregido
    @Override
    public Product update(Product product) {

        Producto entity = productMapper.toProductoUpdate(product);

        if (product.getVendedorId() != null) {
            Usuario vendedor = usuarioCrudRepository.findById(product.getVendedorId())
                    .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));
            entity.setVendedor(vendedor);
        }

        entity = productoCrudRepository.save(entity);

        return productMapper.toProduct(entity);
    }

    // UPDATE STOCK
    @Override
    public Product updateProductStock(Product product) {

        Optional<Producto> opt = productoCrudRepository.findById(product.getProductId());

        if (opt.isEmpty()) {
            return null;
        }

        Producto entity = opt.get();

        entity.setCantidadStock(product.getProductStock());

        entity = productoCrudRepository.save(entity);

        return productMapper.toProduct(entity);
    }
}
