package com.ibra.ecommercePractice.service.impl;

import com.ibra.ecommercePractice.dto.CategoryDto;
import com.ibra.ecommercePractice.dto.Response;
import com.ibra.ecommercePractice.exception.NotFoundException;
import com.ibra.ecommercePractice.mapper.EntityDtoMapper;
import com.ibra.ecommercePractice.model.Category;
import com.ibra.ecommercePractice.repository.CategoryRepository;
import com.ibra.ecommercePractice.service.interf.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImp implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EntityDtoMapper entityDtoMapper;

    @Autowired
    public CategoryServiceImp(CategoryRepository categoryRepository, EntityDtoMapper entityDtoMapper) {
        this.categoryRepository = categoryRepository;
        this.entityDtoMapper = entityDtoMapper;
    }


    @Override
    public Response createCategory(CategoryDto categoryRequest) {
        Category category = new Category();

        category.setName(categoryRequest.getName());

        categoryRepository.save(category);


        return Response.builder()
                .status(201)
                .message("Category created successfully")
                .categoryDto(entityDtoMapper.mapCategoryToCategoryDto(category))
                .build();
    }

    @Override
    public Response getAllCategory() {
        List<Category> categories = categoryRepository.findAll();

        //i could combine the top and the bottom
        List<CategoryDto> categoryDtos = categories.stream()
                .map(entityDtoMapper::mapCategoryToCategoryDto)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .categoryDtoList(categoryDtos)
                .build();
    }

    @Override
    public Response updateCategory(Long categoryId, CategoryDto categoryRequest) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new NotFoundException("Category does not exist"));
        category.setName(categoryRequest.getName());

        categoryRepository.save(category);

        CategoryDto categoryDto = entityDtoMapper.mapCategoryToCategoryDto(category);

        return Response.builder()
                .status(200)
                .message("Category updated successfully")
                .categoryDto(categoryDto)
                .build();
    }

    @Override
    public Response getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new NotFoundException("Category does not exist"));

        CategoryDto categoryDto = entityDtoMapper.mapCategoryToCategoryDto(category);

        return Response.builder()
                .status(200)
                .categoryDto(categoryDto)
                .build();
    }

    @Override
    public Response deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new NotFoundException("Category does not exist"));

        categoryRepository.delete(category);

        return Response.builder()
                .status(200)
                .message("Category deleted successfully")
                .build();
    }
}
