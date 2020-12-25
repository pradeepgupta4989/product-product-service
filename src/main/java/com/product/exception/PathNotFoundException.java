/*
 * Copyright (c) 2018 The Emirates Group. All Rights Reserved. The information specified here is confidential and remains property of the Emirates Group.
 * groupId     - com.emirates.ocsl
 * artifactId  - ocsl-dom
 * name        - ocsl-dom
 * description - OCSL Domain Object Model Project
 * 2019
 */
package com.product.exception;

import org.springframework.http.HttpStatus;

import java.util.Collection;

/**
 * <p>
 * PathNotFoundException.
 * </p>
 *
 * @author Markus Antonius
 */
@DomainException(
    errorMessage = "Resource Not Available. Kindly check and retry.",
    errorHttpStatus = HttpStatus.NOT_FOUND,
    errorSequence = "001")
public class PathNotFoundException extends DomainBaseException {

    static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Domain base exception.
     *
     * @param errors the errors
     */
    public PathNotFoundException(final Collection<DomainError> errors) {
        super(errors);
    }

    /**
     * Instantiates a new Domain base exception.
     *
     * @param cause the cause
     */
    public PathNotFoundException(final String cause) {
        super(cause);
    }
}
