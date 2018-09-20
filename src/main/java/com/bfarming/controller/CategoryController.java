package com.bfarming.controller;

import com.bfarming.model.Category;
import com.bfarming.response.ApiResponse;
import com.bfarming.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    CategoryService categoryService;

    @GetMapping("/allCategories")
    public List<Category> allCategories() {

        return categoryService.findAll();

    }

    @PostMapping("/addCategory")
    public ApiResponse<?> addCategory(@Valid @RequestBody Category category) {

        logger.info("Inside create category");
        Category categoryExists = categoryService.findCategoryByCategoryName(category.getCategoryName());
        if (categoryExists != null) {
            return new ApiResponse<>("\"There is already a category exists with the category name provided provided\"", com.bfarming.response.ResponseStatus.getValidResponseStatus(HttpStatus.OK));
        } else {
            categoryService.save(category);
            return new ApiResponse<>("\"Category details successfully saved in the database\"");
        }

    }

    @PutMapping("/editCategory/{id}")
    public ApiResponse<?> editCategory(@PathVariable(value = "id") Long categoryId, @Valid @RequestBody Category category) {

        logger.info("Inside create category");
        Category categoryExists = categoryService.findById(categoryId);
        if (categoryExists == null) {

            return new ApiResponse<>("\"Category of id not found\"", com.bfarming.response.ResponseStatus.getValidResponseStatus(HttpStatus.OK));

        } else {

            categoryExists.setCategoryName(category.getCategoryName());
            categoryService.save(categoryExists);
            return new ApiResponse<>("\"Category details successfully updated in the database\"");
        }

    }


}
