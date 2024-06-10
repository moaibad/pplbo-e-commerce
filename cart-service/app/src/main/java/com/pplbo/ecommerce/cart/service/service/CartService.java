package com.pplbo.ecommerce.cart.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pplbo.ecommerce.cart.service.dto.CartProductRequest;
import com.pplbo.ecommerce.cart.service.dto.CartProductResponse;
import com.pplbo.ecommerce.cart.service.dto.CartRequest;
import com.pplbo.ecommerce.cart.service.model.Cart;
import com.pplbo.ecommerce.cart.service.model.Product;
import com.pplbo.ecommerce.cart.service.model.ProductToBuy;
import com.pplbo.ecommerce.cart.service.repository.CartRepository;
import com.pplbo.ecommerce.cart.service.repository.ProductRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Optional<Cart> getCartById(Long id) {
        return cartRepository.findById(id);
    }

    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    public void addProductToCart(Long cartId, Long productId, Integer quantityToBuy) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + productId));

        Cart cart = optionalCart.orElseThrow(() -> new NoSuchElementException("Cart not found with ID: " + cartId));

        // Check if the product already exists in the cart
        for (ProductToBuy productToBuy : cart.getProductsToBuy()) {
            if (productToBuy.getProduct().getId().equals(productId)) {
                // Product already exists, update quantity to buy
                int newQuantityToBuy = productToBuy.getQuantityToBuy() + quantityToBuy;
                if (newQuantityToBuy > product.getQuantity()) {
                    throw new IllegalArgumentException(
                            "Not enough quantity available for product: " + product.getName());
                }
                productToBuy.setQuantityToBuy(newQuantityToBuy);
                return;
            }
        }

        // Product does not exist in the cart, add new ProductToBuy
        if (quantityToBuy > product.getQuantity()) {
            throw new IllegalArgumentException("Not enough quantity available for product: " + product.getName());
        }

        ProductToBuy productToBuy = ProductToBuy.builder()
                .product(product)
                .cart(cart)
                .quantityToBuy(quantityToBuy)
                .build();

        cart.getProductsToBuy().add(productToBuy);
    }
}
