package com.product.router;

import com.product.handler.ErrorHandler;
import com.product.handler.ProductHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class ProductRouter {

	@Bean
	public RouterFunction<ServerResponse> route(ProductHandler productHandler, ErrorHandler errorHandler) {
		
		final Consumer<ServerRequest> requestConsumer = serverRequest -> 
			log.info("Request path: {}, params: {}, variables: {}, headers: {}", 
					serverRequest.path(),
					serverRequest.queryParams().toString(), 
					serverRequest.pathVariables().toString(),
					serverRequest.headers().asHttpHeaders().entrySet().toString());
		
		return RouterFunctions
				.route(RequestPredicates.GET("/product/{productID}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
						productHandler::getProductById);
		 
	}
}
