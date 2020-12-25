/*
 * Copyright (c) 2018 The Emirates Group. All Rights Reserved. The information specified here is confidential and remains property of the Emirates Group.
 * groupId     - com.emirates.ocsl
 * artifactId  - profile-service
 * name        - profile-service
 * description - Profile Service
 * 2019
 */
package com.product.handler;

import com.product.exception.DomainError;
import com.product.exception.DomainException;
import com.product.exception.ThrowableTranslator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiConsumer;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

/**
 * The type Error handler.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ErrorHandler {

    private static final String FUNCTIONAL_ERROR_MESSAGE = "Error Occurred while calling service";

    private final ThrowableTranslator throwableTranslator;

    private final BiConsumer<Throwable, Throwable> stackTraceLogging = (Throwable stackTrace1, Throwable stackTrace2) ->
        log.error("{}{}", FUNCTIONAL_ERROR_MESSAGE, stackTrace1.getStackTrace());

    private <T extends Throwable> Mono<ServerResponse> getResponse(final Mono<T> monoError) {
        return monoError
            .flatMap(throwable -> throwableTranslator.apply(throwable)
                .flatMap(errorList -> ServerResponse
                    .status(errorList.stream()
                        .findFirst()
                        .map(DomainError::getHttpStatus)
                        .orElseGet(() -> DomainException.DEFAULT_HTTP_STATUS)
                    )
                    .body(fromObject(errorList))));
    }
    /**
     * Throwable error mono.
     * @param error the error
     * @return the mono
     */
    public Mono<ServerResponse> throwableError(final Throwable error) {
        return Mono.just(error).doAfterSuccessOrError(stackTraceLogging).transform(this::getResponse);
    }
}
