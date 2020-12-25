package com.product.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.product.model.Product;

public class ProductMapper{

	public static Product map(Product product, JsonNode externalReview) {
		product.setExternalReview(externalReview);
		return product;
	}
	public static boolean valid(JsonNode external){
		if(null != external)
			return true;
		else return false;
	}
}
