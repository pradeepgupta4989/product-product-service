package com.product.router;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.ProductReviewApplication;
import com.product.handler.ProductHandler;
import com.product.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.io.File;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes= ProductReviewApplication.class)
public class ProductRouterTest
{
    @Autowired
    private WebTestClient webClient;
    @MockBean
    //private ProductService productService;
    private ProductHandler productHandler;
    @Test
    public void testGetProductReviewFromInternalAndExternalSources() {
        Product product = getProductReviews();
        given(productHandler.getProductById(any(ServerRequest.class))).willReturn(
                ok().contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(Mono.just(product), Product.class));
                //.body(Mono.just(product), ProductDTO.class));

        webClient.get()
                .uri("/product/M20324")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Product.class)
                .isEqualTo(product);

        Mockito.verify(productHandler, times(1)).getProductById(any(ServerRequest.class));
    }

    private Product getProductReviews() {
        Product product = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            product = objectMapper.readValue(new File("src/test/resources/IntegratedProductReviewResponse.json"), Product.class);
        }catch(Exception e){
            e.printStackTrace();
        }
        return product;
    }
}