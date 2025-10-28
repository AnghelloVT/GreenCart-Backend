package com.GreenCart.GreenCart.persistance;

import com.GreenCart.GreenCart.domain.Category;
import com.GreenCart.GreenCart.domain.repository.CategoryRepository;
import com.GreenCart.GreenCart.persistance.crud.CategoriaCrudRepository;
import com.GreenCart.GreenCart.persistance.entity.Categoria;
import com.GreenCart.GreenCart.persistance.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoriaRepository implements CategoryRepository {

    @Autowired
    private CategoriaCrudRepository categoriaCrudRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getActiveCategories() {
        List<Categoria> categorias = categoriaCrudRepository.findByEstadoTrue();
        return categoryMapper.toCategories(categorias);
    }

    @Override
    public List<Category> getAllCategories() {
        List<Categoria> categorias = (List<Categoria>) categoriaCrudRepository.findAll();
        return categoryMapper.toCategories(categorias);
    }

    @Override
    public Optional<Category> getCategoryById(int id) {
        return categoriaCrudRepository.findById(id).map(categoryMapper::toCategory);
    }

    @Override
    public Category save(Category category) {
        Categoria categoria = categoryMapper.toCategoria(category);
        categoria = categoriaCrudRepository.save(categoria);
        return categoryMapper.toCategory(categoria);
    }

    @Override
    public void delete(int id) {
        categoriaCrudRepository.deleteById(id);
    }
}