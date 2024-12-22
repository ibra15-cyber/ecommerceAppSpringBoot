package com.ibra.ecommercePractice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibra.ecommercePractice.dto.ProductDto;
import com.ibra.ecommercePractice.dto.Response;
import com.ibra.ecommercePractice.model.Product;
import com.ibra.ecommercePractice.service.interf.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.DataInput;
import java.io.IOException;
import java.security.DrbgParameters;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/get-all")
    public ResponseEntity<Response> getAllProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts());
    }

    @GetMapping("/get-product-by-Id/{productId}")
    public ResponseEntity<Response> getProductById(@PathVariable("productId") Long productId){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(productId));
    }

    @GetMapping("/get-product-by-categrory/{categoryId}")
    public ResponseEntity<Response> getProductByCategory(@PathVariable("categoryId") Long categoryId){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductsByCategory(categoryId));
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchProduct(@RequestParam String searchValue){
        return ResponseEntity.status(HttpStatus.OK).body(productService.searchProducts(searchValue));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Response> createProduct(@RequestBody ProductDto productDto) throws IOException {
//        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDto));
//    }
    public ResponseEntity<Response> createProduct(@RequestPart ProductDto productDto, @RequestPart MultipartFile image) throws IOException {
//        try {
            // Convert JSON to ProductDTO
//            ObjectMapper objectMapper = new ObjectMapper();
//            ProductDto productDto = objectMapper.readValue(metadata, ProductDto.class);

            // Delegate to service
//            Product createdProduct = ;
            return ResponseEntity.ok(productService.createProduct(productDto, image));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{productId}")
    public ResponseEntity<Response> updateProduct(@PathVariable("productId") Long productDtoId, @RequestBody ProductDto productDto) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.updateProduct(productDtoId, productDto));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Response> deleteProduct(@PathVariable("productId") Long productDtoId) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.deleteProduct(productDtoId));
    }
}
