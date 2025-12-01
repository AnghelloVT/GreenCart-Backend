package com.GreenCart.GreenCart.domain.service;

import org.springframework.web.multipart.MultipartFile;

import com.GreenCart.GreenCart.domain.Product;
import com.GreenCart.GreenCart.domain.repository.ProductRepository;
import com.GreenCart.GreenCart.persistance.entity.Producto;
import com.GreenCart.GreenCart.persistance.mapper.ProductMapper;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper mapper;

    @Autowired
    private Cloudinary cloudinary;

    // ✔ Obtener todos (Product DTO)
    public List<Product> getAll() {
        return productRepository.getAll();
    }

    public Optional<Product> getProduct(int productId) {
        return productRepository.getProduct(productId);
    }

    public Optional<List<Product>> getByCategory(int categoryId) {
        return productRepository.getByCategory(categoryId);
    }

    //CREAR PRODUCTO
    public Product save(Product productDto, MultipartFile file) throws IOException {

        //subir imagen a Cloudinary
        Map uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap("folder", "greencart")
        );

        String url = uploadResult.get("secure_url").toString();
        productDto.setProductImage(url);

        //Convertir DTO → Entity
        Producto entity = mapper.toProductoCreate(productDto);

        //Guardar en BD
        return productRepository.save(productDto);
    }

    //ELIMINAR
    public boolean delete(int productId) {
        return productRepository.getProduct(productId).map(product -> {
            productRepository.delete(productId);
            return true;
        }).orElse(false);
    }

    //Filtrar por vendedor
    public List<Product> getByVendedor(Long vendedorId) {
        return productRepository.getByVendedor(vendedorId);
    }

    //UPDATE CON CLOUDINARY
    public Product update(Product productDto, MultipartFile file) throws IOException {

        if (file != null && !file.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap("folder", "greencart")
            );
            productDto.setProductImage(uploadResult.get("secure_url").toString());
        }

        return productRepository.update(productDto);
    }

    public Product updateStock(Product product) {
        return productRepository.updateProductStock(product);
    }
}
