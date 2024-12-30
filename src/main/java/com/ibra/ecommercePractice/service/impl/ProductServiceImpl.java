package com.ibra.ecommercePractice.service.impl;

import com.ibra.ecommercePractice.dto.CategoryDto;
import com.ibra.ecommercePractice.dto.ProductDto;
import com.ibra.ecommercePractice.dto.Response;
import com.ibra.ecommercePractice.exception.NotFoundException;
import com.ibra.ecommercePractice.mapper.EntityDtoMapper;
import com.ibra.ecommercePractice.model.Category;
import com.ibra.ecommercePractice.model.Product;
import com.ibra.ecommercePractice.repository.CategoryRepository;
import com.ibra.ecommercePractice.repository.ProductRepository;
import com.ibra.ecommercePractice.utility.cloudinary.CloudinaryUploadService;
import com.ibra.ecommercePractice.service.interf.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final EntityDtoMapper entityDtoMapper;
    private final CategoryRepository categoryRepository;
    private final CloudinaryUploadService cloudinaryUploadService;

    public ProductServiceImpl(ProductRepository productRepository, EntityDtoMapper entityDtoMapper, CategoryRepository categoryRepository, CloudinaryUploadService cloudinaryUploadService) {
        this.productRepository = productRepository;
        this.entityDtoMapper = entityDtoMapper;
        this.categoryRepository = categoryRepository;
        this.cloudinaryUploadService = cloudinaryUploadService;
    }


    @Override
    public Response createProduct(ProductDto productDto, MultipartFile image) throws IOException {

        Category category = categoryRepository.findById(productDto.getCategoryDto().getId()).orElseThrow(()-> new NotFoundException("Category not found"));

        System.out.println(category);
        System.out.println(productDto);

//        System.out.println(productDto.getImageFile());

        String  imageUrl = cloudinaryUploadService.uploadFile(image);

        System.out.println(imageUrl);

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(imageUrl);
        product.setCategory(category);

        System.out.println(product);

        productRepository.save(product);

        ProductDto productDto1 = entityDtoMapper.mapProductToProductDto(product);
        CategoryDto categoryDto = entityDtoMapper.mapCategoryToCategoryDto(category);
        return Response.builder()
                .status(201)
                .productDto(productDto1)
                .categoryDto(categoryDto)
                .message("Product created successfully")
                .build();
    }

    @Override
    public Response getAllProducts() {
        List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<ProductDto> productDtos = products.stream().map(entityDtoMapper::mapProductToProductDto).collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .message("Products retrieved successfully")
                .productListDto(productDtos)
                .build();
    }

    @Override
    @Cacheable(key = "#productId", value = "products")
    public Response getProductById(Long productId) {
        System.out.println("called getProductById from the DB");
        Product product = productRepository.findById(productId).orElseThrow(()-> new NotFoundException("Product does not exist"));
        Category category = product.getCategory();

        ProductDto productDto = entityDtoMapper.mapProductToProductDto(product);
        CategoryDto categoryDto = entityDtoMapper.mapCategoryToCategoryDto(category);

        return Response.builder()
                .status(200)
                .productDto(productDto)
                .categoryDto(categoryDto)
                .build();
    }

    @Override
    @CachePut(value = "products", key = "#productDtoId")
    public Response updateProduct(Long productDtoId, ProductDto productDto) {
        System.out.println("called update product from the DB");

        // video 7 12:40
        Product product = productRepository.findById(productDtoId).orElseThrow(()-> new NotFoundException("product does not exit"));
        Category category = product.getCategory();

        product.setCategory(category);
        product.setImageUrl(productDto.getImgUrl());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());

        productRepository.save(product);

        return Response.builder()
                .status(201)
                .message("Product updated successfully")
                .productDto(productDto)
                .build() ;
    }

    @Override
    @CacheEvict(value = "products", key = "#productDtoId")
    public Response deleteProduct(Long productDtoId) {
        System.out.println("called delete from the DB");

        Product product = productRepository.findById(productDtoId).orElseThrow(()-> new NotFoundException("product does not exit"));

        productRepository.delete(product);

        return Response.builder()
                .status(200)
                .message("product deleted successfully")
                .build();
    }

    @Override
    public Response getProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new NotFoundException("Category not find."));

        List<Product> products = productRepository.findAllByCategoryId(category.getId());

        List<ProductDto> productDtos = products.stream().map(entityDtoMapper::mapProductToProductDto).toList();
        return Response.builder()
                .status(200)
                .message("Category fetched successfully")
                .productListDto(productDtos)
                .build();
    }

    @Override
    public Response searchProducts(String searchValue) {
        List<Product> searchProducts = productRepository.findByNameOrDescriptionContaining(searchValue, searchValue);

        if(searchProducts.isEmpty()){
            throw new NotFoundException("Cant find searched product");
        }
        List<ProductDto> seachProductsDtoList = searchProducts.stream().map(entityDtoMapper::mapProductToProductDto).toList();
        return Response.builder()
                .status(200)
                .productListDto(seachProductsDtoList)
                .build();
    }
}

