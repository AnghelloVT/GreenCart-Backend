package com.GreenCart.GreenCart.domain.service;

import com.GreenCart.GreenCart.domain.Category;
import com.GreenCart.GreenCart.persistance.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

   
    public List<Category> getCategoriasActivas() {
        return categoriaRepository.getActiveCategories();
    }

    
    public List<Category> getAllCategories() {
        return categoriaRepository.getAllCategories();
    }

    
    public Optional<Category> getCategoryById(int id) {
        return categoriaRepository.getCategoryById(id);
    }

   
    public Category saveCategory(Category category) {
        return categoriaRepository.save(category);
    }

    
    public void deleteCategory(int id) {
        categoriaRepository.delete(id);
    }
}