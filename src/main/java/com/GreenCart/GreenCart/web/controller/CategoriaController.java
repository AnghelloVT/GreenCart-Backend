package com.GreenCart.GreenCart.web.controller;

import com.GreenCart.GreenCart.domain.Category;
import com.GreenCart.GreenCart.domain.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/activas")
    public List<Category> getCategoriasActivas() {
        return categoriaService.getCategoriasActivas();
    }

    @GetMapping
    public List<Category> getAllCategorias() {
        return categoriaService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategoriaById(@PathVariable int id) {
        return categoriaService.getCategoryById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + id));
    }

    @PostMapping
    public Category saveCategoria(@RequestBody Category category) {
        return categoriaService.saveCategory(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategoria(@PathVariable int id) {
        categoriaService.deleteCategory(id);
    }
}