package com.product.handler;

import com.product.exception.PathNotFoundException;
import com.product.model.Product;
import com.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductHandler {

	@Autowired
	private ProductService productService;
	private final ErrorHandler errorHandler;


	public Mono<ServerResponse> getProductById(final ServerRequest request) {
		log.debug("Inside getProductById method with id : " + request.pathVariable("productID"));
		return productService.getProductReviewByProductId(request.pathVariable("productID"))
				.flatMap(productReview -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
						.body(Mono.just(productReview), Product.class))
				.switchIfEmpty(Mono.error(new PathNotFoundException("Product with ProductID not Found")))
		        .onErrorResume(errorHandler::throwableError);
	}

}
