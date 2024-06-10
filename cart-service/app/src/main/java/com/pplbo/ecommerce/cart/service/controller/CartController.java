package com.pplbo.ecommerce.cart.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pplbo.ecommerce.cart.service.dto.CartProductRequest;
import com.pplbo.ecommerce.cart.service.dto.CartRequest;
import com.pplbo.ecommerce.cart.service.dto.ProductRequest;
import com.pplbo.ecommerce.cart.service.dto.ProductResponse;
import com.pplbo.ecommerce.cart.service.model.Cart;
import com.pplbo.ecommerce.cart.service.service.CartService;
import com.pplbo.ecommerce.cart.service.service.ProductService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    @Autowired
    private final CartService cartService;

    @Autowired
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cart createCart(@RequestBody CartRequest cartRequest) {
        return cartService.createCart(cartRequest);
    }

    @PostMapping("/{id}/add")
    public Cart addProductToCart(@PathVariable Long id, @RequestBody CartProductRequest productToAdd) {
        return cartService.addProductToCart(id, productToAdd);
    }

    @PostMapping("/product/add")
    public ProductResponse addProductData(@RequestBody ProductRequest productToAdd) {
        return productService.createProduct(productToAdd);
    }

    @GetMapping
    public List<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    @GetMapping("/{id}")
    public Cart getCartById(@PathVariable Long id) {
        return cartService.getCartById(id);
    }

    @PutMapping("/{id}")
    public Cart updateCart(@PathVariable Long id, @RequestBody CartRequest cartRequest) {
        return cartService.updateCart(id, cartRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCart(@PathVariable Long id) {
        cartService.deleteCartById(id);
    }
}
