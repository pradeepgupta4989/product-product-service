/*
 * Copyright (c) 2018 The Emirates Group. All Rights Reserved. The information specified here is confidential and remains property of the Emirates Group.
 * groupId     - com.emirates.ocsl
 * artifactId  - ocsl-dom
 * name        - ocsl-dom
 * description - OCSL Domain Object Model Project
 * 2019
 */
package com.product.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Domain base exception.
 */
@Getter public class DomainBaseException extends RuntimeException {

    private static final long serialVersionUID = -8151947739735047444L;
    private final Collection<DomainError> errors  = new ArrayList<>();

    protected DomainBaseException(final Collection<DomainError> errors) {
        super(Optional.ofNullable(errors)
            .map(ocslErrors -> ocslErrors.stream()
                .map(DomainError::getErrorMessage)
                .collect(Collectors.joining(", ")))
            .orElse(DomainException.DEFAULT_MESSAGE));

        initialise(Optional.empty());
        Optional.ofNullable(errors).ifPresent(this.errors::addAll);
    }

    protected DomainBaseException(final String cause) {
        super(new RuntimeException(cause));
        initialise(Optional.of(super.getCause()));
    }

    private void initialise(final Optional<Throwable> optionalThrowable) {

        final Class<?> exception = getClass();
        if (exception.isAnnotationPresent(DomainException.class)) {
            final DomainException annotation = exception.getAnnotation(DomainException.class);
            errors.add(DomainError.builder()
                .errorMessage(new StringBuilder()
                    .append(annotation.errorMessage())
                    .append(optionalThrowable.map(Throwable::getMessage).orElse(""))
                    .toString())
                .httpStatus(annotation.errorHttpStatus())
                .errorSequence(annotation.errorSequence())
                .build());
        }
        else {
            errors.add(DomainError.builder()
                .errorMessage(DomainException.DEFAULT_MESSAGE)
                .httpStatus(DomainException.DEFAULT_HTTP_STATUS)
                .errorSequence(DomainException.DEFAULT_SEQUENCE)
                .build());
        }
    }

}
