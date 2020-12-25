/*
 * Copyright (c) 2018 The Emirates Group. All Rights Reserved. The information specified here is confidential and remains property of the Emirates Group.
 * groupId     - com.emirates.ocsl
 * artifactId  - profile-service
 * name        - profile-service
 * description - Profile Service
 * 2019
 */
package com.product.exception;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The type Throwable translator.
 */
@Component
public class ThrowableTranslator extends GenericThrowableTranslator implements Function<Throwable, Mono<Collection<DomainError>>> {

    private final BiFunction<Throwable, Collection<DomainError>, Collection<DomainError>> locationDefinition = (throwable, domainErrors) ->
        domainErrors.stream()
            .map(eachError -> {
                DomainError.Location errorLocation;
                switch (throwable.getClass().getName()) {
                    case "com.couchbase.client.java.error.DocumentAlreadyExistsException":
                    case "com.couchbase.client.java.error.DocumentDoesNotExistException":
                    case "org.springframework.dao.OptimisticLockingFailureException":
                        errorLocation = DomainError.Location.COUCHBASE;
                        break;
                    default:
                        errorLocation = DomainError.Location.SERVICE;
                        break;
                }
                return eachError.apply(errorLocation);
            })
            .collect(Collectors.toList());

    @Override public Mono<Collection<DomainError>> apply(final Throwable throwable) {
        return Mono.just(
            locationDefinition.apply(throwable, genericTranslation.apply(throwable))
        );
    }
}
