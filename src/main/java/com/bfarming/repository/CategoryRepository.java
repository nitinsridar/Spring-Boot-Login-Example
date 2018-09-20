package com.bfarming.repository;

import com.bfarming.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

    Category findByCategoryName(String categoryName);

    @Override
    List<Category> findAll();

}



