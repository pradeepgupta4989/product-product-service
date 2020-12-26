package com.product.model;

import lombok.*;

@Data
@EqualsAndHashCode
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Review {
    private String reviewId;
    private String reviewComment;
    private double rating;
}
