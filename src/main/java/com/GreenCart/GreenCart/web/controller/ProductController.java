package com.GreenCart.GreenCart.web.controller;
import com.GreenCart.GreenCart.domain.Product;
import com.GreenCart.GreenCart.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public List<Product> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Product> getProduct(@PathVariable("id") int productId) {
        return productService.getProduct(productId);
    }

    @GetMapping("/vendedor/{vendedorId}")
    public List<Product> getByVendedor(@PathVariable Long vendedorId) {
        return productService.getByVendedor(vendedorId);
    }

    @GetMapping("/category/{categoryId}")
    public Optional<List<Product>> getByCategory(@PathVariable("categoryId") int categoryId) {
        return productService.getByCategory(categoryId);
    }

    // Cambio aquí: Recibe URL en lugar de archivo
    @PostMapping("/save")
    public Product save(@RequestPart("product") Product product,
                        @RequestParam(value = "imageUrl", required = false) String imageUrl) throws IOException {
        return productService.save(product, imageUrl);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") int productId) {
        return productService.delete(productId);
    }

    @PutMapping(value = "/update/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Product> updateProduct(
            @PathVariable int id,
            @ModelAttribute Product product,
            @RequestParam(value = "imageUrl", required = false) String imageUrl
    ) throws IOException {
        product.setProductId(id);
        Product updated = productService.update(product, imageUrl);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/actualizar-stock/{id}")
    public ResponseEntity<Product> actualizarStock(@PathVariable int id, @RequestBody Product product) {
        product.setProductId(id);
        return ResponseEntity.ok(productService.updateStock(product));
    }
}
