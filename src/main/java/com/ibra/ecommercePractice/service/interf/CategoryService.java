package com.ibra.ecommercePractice.service.interf;

import com.ibra.ecommercePractice.dto.CategoryDto;
import com.ibra.ecommercePractice.dto.Response;

public interface CategoryService {
    Response createCategory(CategoryDto categoryRequest);
    Response getAllCategory();
    Response updateCategory(Long categoryId, CategoryDto categoryRequest);
    Response getCategoryById(Long categoryId);
    Response deleteCategory(Long categoryId);

}
