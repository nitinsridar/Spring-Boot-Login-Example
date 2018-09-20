package com.bfarming.service;

import com.bfarming.exception.ResourceNotFoundException;
import com.bfarming.model.Category;
import com.bfarming.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CategoryService")
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {

        return  categoryRepository.findAll();
    }

    @Override
    public void save(Category category) {

        categoryRepository.save(category);

    }

    @Override
    public Category findCategoryByCategoryName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }


}