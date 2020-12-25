package com.product.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

import java.util.List;
@Setter
@Data
@EqualsAndHashCode
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Product {
	private String productID;
	private List<Review> reviews;
	private JsonNode externalReview;
}
