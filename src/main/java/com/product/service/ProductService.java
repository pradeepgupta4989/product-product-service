package com.product.service;


import com.product.model.Product;
import reactor.core.publisher.Mono;


public interface ProductService {

    Mono<Product> getProductReviewByProductId(String productID);
}
