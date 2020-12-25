package com.product.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.product.mapper.ProductMapper;
import com.product.model.Product;
import com.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ProductReviewServiceImpl implements ProductService {
	@Autowired
	private WebClient.Builder webClientBuilder;

	@Value( "${product.review.service.url}" )
	private String productReviewServiceURL;
	@Value( "${product.review.external.url}" )
	private String productReviewExternalURL;

	@Override
	public Mono<Product> getProductReviewByProductId(String productID) {
		log.debug("Inside GetProductById method with productID = {}", productID);

		Mono<JsonNode> externalReview = webClientBuilder.build().get().
				uri(productReviewExternalURL, productID).
				retrieve().
				bodyToMono(JsonNode.class);

		return webClientBuilder.build().get().
				uri(productReviewServiceURL, productID).
				retrieve().
				bodyToMono(Product.class).
				flatMap(a-> mergeReviews(a, externalReview));
	}

	protected Mono<Product> mergeReviews(Product product, Mono<JsonNode> externalReview){
			return 	externalReview.map(ext -> ProductMapper.map(product, ext));
	}
}
