package com.ibra.ecommercePractice.controller;

import com.ibra.ecommercePractice.dto.CategoryDto;
import com.ibra.ecommercePractice.dto.Response;
import com.ibra.ecommercePractice.service.impl.CategoryServiceImp;
import com.ibra.ecommercePractice.service.interf.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }



    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Response> createCategory(@RequestBody CategoryDto categoryDto){
         return ResponseEntity.status(HttpStatus.OK).body(categoryService.createCategory(categoryDto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Response> getAllCategory(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAllCategory());
    }


    @GetMapping("/get-category-by-id/{categroyId}")
    public ResponseEntity<Response> getCategoryById(@PathVariable("categroyId") Long categoryId){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategoryById(categoryId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{categoryId}")
    public ResponseEntity<Response> updateCategory(@PathVariable("categoryId") Long categoryId, @RequestBody CategoryDto categoryDto){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateCategory(categoryId, categoryDto));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<Response> deleteCategory(@PathVariable("categoryId") Long categoryId){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.deleteCategory(categoryId));
    }
}
