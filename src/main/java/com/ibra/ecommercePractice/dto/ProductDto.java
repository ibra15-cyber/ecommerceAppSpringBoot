package com.ibra.ecommercePractice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto implements Serializable {
    @Serial
    private final static long serialVersionUID = 1L;

    private Long id;

    private String name;
    private String description;
//    private MultipartFile imageFile;
    private String imgUrl;
    private BigDecimal price;
    private CategoryDto categoryDto;
    private LocalDateTime createdAt;
}
