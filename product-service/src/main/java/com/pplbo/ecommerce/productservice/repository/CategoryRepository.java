package com.pplbo.ecommerce.productservice.repository;

import com.pplbo.ecommerce.productservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}