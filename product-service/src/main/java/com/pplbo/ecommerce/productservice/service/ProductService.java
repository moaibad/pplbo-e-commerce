package com.pplbo.ecommerce.productservice.service;

import com.pplbo.ecommerce.productservice.dto.CategoryResponse;
import com.pplbo.ecommerce.productservice.dto.ProductRequest;
import com.pplbo.ecommerce.productservice.dto.ProductResponse;
import com.pplbo.ecommerce.productservice.model.Product;
import com.pplbo.ecommerce.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.name())
                .price(productRequest.price())
                .description(productRequest.description())
                .brandId(productRequest.brandId())
                .image(productRequest.image())
                .build();
        productRepository.save(product);
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getDescription(), product.getBrandId(), product.getImage());
    }

    public List<ProductResponse> getAllProducts(){
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getDescription(), product.getBrandId(), product.getImage()))
                .collect(Collectors.toList());
    }

    public ProductResponse updateProduct(Integer id, ProductRequest productRequest){
        Product product = productRepository.findById(id).orElse(null);
        product.setName(productRequest.name());
        product.setPrice(productRequest.price());
        product.setDescription(productRequest.description());
        product.setBrandId(productRequest.brandId());
        product.setImage(productRequest.image());
        productRepository.save(product);

        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getDescription(), product.getBrandId(), product.getImage());
    }

    public void deleteProduct(Integer id){
        productRepository.deleteById(id);
    }

    public ProductResponse getProductById(Integer id){
        Product product = productRepository.findById(id).orElse(null);
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getDescription(), product.getBrandId(), product.getImage());
    }

}