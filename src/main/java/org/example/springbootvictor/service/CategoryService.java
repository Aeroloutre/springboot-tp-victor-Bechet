package org.example.springbootvictor.service;

import org.example.springbootvictor.model.Category;
import org.example.springbootvictor.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryService {

    private static CategoryRepository categoryRepository = null;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public static List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public static Category getCategory(@PathVariable Long id) {
        return categoryRepository.findById(id).get();
    }

    public static Category createCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    public static Category updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        category.setName(categoryDetails.getName());

        return categoryRepository.save(category);
    }

    public static void deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
    }
}
