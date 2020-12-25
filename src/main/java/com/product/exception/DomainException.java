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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interface Domain exception.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface DomainException {

    /**
     * The constant DEFAULT_MESSAGE.
     */
    String DEFAULT_MESSAGE = "Please contact Support Team";

    /**
     * The constant DEFAULT_HTTP_STATUS.
     */
    HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    /**
     * The constant DEFAULT_SEQUENCE.
     */
    String DEFAULT_SEQUENCE = "001";

    /**
     * Error message string.
     *
     * @return the string
     */
    String errorMessage() default DEFAULT_MESSAGE;

    /**
     * Error http status http status.
     *
     * @return the http status
     */
    HttpStatus errorHttpStatus();

    /**
     * Error code string.
     *
     * @return the string
     */
    String errorSequence() default DEFAULT_SEQUENCE;

}
