package com.bfarming.service;

import com.bfarming.model.Category;
import  java.util.*;

public interface CategoryService {

    List<Category> findAll();

    void save(Category category);

    Category findCategoryByCategoryName(String categoryName);

    Category findById(Long id);
}
