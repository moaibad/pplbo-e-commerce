package com.pplbo.ecommerce.cart.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pplbo.ecommerce.cart.service.dto.CartProductRequest;
import com.pplbo.ecommerce.cart.service.dto.CartProductResponse;
import com.pplbo.ecommerce.cart.service.dto.CartRequest;
import com.pplbo.ecommerce.cart.service.model.Cart;
import com.pplbo.ecommerce.cart.service.model.Product;
import com.pplbo.ecommerce.cart.service.repository.CartRepository;
import com.pplbo.ecommerce.cart.service.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    public Cart generateCart(CartRequest cartRequest) {
        Cart cart = Cart.builder()
                .userID(cartRequest.userID())
                .products(cartRequest.products())
                .totalPrice(cartRequest.totalPrice())
                .build();
        return cart;
    }

    public Cart createCart(CartRequest cartRequest) {
        Cart cart = generateCart(cartRequest);
        List<Product> products = cart.getProducts();
        productRepository.saveAll(products);
        cartRepository.save(cart);
        return cart;
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart getCartById(Long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        return cart.get();
    }

    public Cart updateCart(Long id, CartRequest cartRequest) {
        Optional<Cart> result = cartRepository.findById(id);
        // Update the fields of the cart entity with the new values from the cart
        // request
        Cart cart = result.get();
        cart.setProducts(cartRequest.products());
        cart.setTotalPrice(cartRequest.totalPrice());
        return cartRepository.save(cart);
    }

    public Cart addProductToCart(Long id, CartProductRequest cartProductRequest) {
        Optional<Cart> result = cartRepository.findById(id);
        Cart cart = result.get();
        List<Product> products = cart.getProducts();
        Optional<Product> query = productRepository.findById(cartProductRequest.product().getId());
        if (query.isPresent()) {
            products.add(cartProductRequest.product());
            Long totalPrice = cart.getTotalPrice()
                    + (cartProductRequest.product().getPrice() * cartProductRequest.totalPrice());
            cart.setTotalPrice(totalPrice);
        }
        // Save the cart
        return cartRepository.save(cart);
    }

    public Cart deleteCartById(Long id) {
        Optional<Cart> result = cartRepository.findById(id);
        return result.get();
    }

    public void removeProductFromCart(Long cartId, Long productId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cart.getProducts().removeIf(product -> product.getId().equals(productId));
            cartRepository.save(cart);
        } else {
        }
    }
}
