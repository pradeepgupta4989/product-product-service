/*
 * Copyright (c) 2018 The Emirates Group. All Rights Reserved. The information specified here is confidential and remains property of the Emirates Group.
 * groupId     - com.emirates.ocsl
 * artifactId  - ocsl-dom
 * name        - ocsl-dom
 * description - OCSL Domain Object Model Project
 * 2019
 */
package com.product.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.function.Function;

/**
 * The type Domain error.
 */
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DomainError implements Function<DomainError.Location, DomainError> {

    private String errorCode;
    private String errorMessage;
    @JsonIgnore
    private HttpStatus httpStatus;
    @JsonIgnore
    private String     errorSequence;

    @Override public DomainError apply(final Location errorLocation) {
        return toBuilder()
            .errorCode(new StringBuilder()
                .append(this.httpStatus.value()).append(".")
                .append(errorLocation.code).append(".")
                .append(this.errorSequence)
                .toString())
            .build();
    }

    public enum Location {

        COUCHBASE("021"),
        SERVICE("020");

        private String code;

        Location(final String code) {
            this.code = code;
        }

        public String code() {
            return code;
        }
    }
}
