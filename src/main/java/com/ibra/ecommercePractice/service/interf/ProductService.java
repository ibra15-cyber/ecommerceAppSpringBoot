package com.ibra.ecommercePractice.service.interf;

import com.ibra.ecommercePractice.dto.ProductDto;
import com.ibra.ecommercePractice.dto.Response;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {

    Response createProduct(ProductDto productDto, MultipartFile image) throws IOException;
    Response getAllProducts();
    Response getProductById(Long productDtoId);
    Response updateProduct(Long productId, ProductDto productDto);
    Response deleteProduct(Long productDtoId);
    Response getProductsByCategory(Long categoryId);
    Response searchProducts(String searchValue);
}
