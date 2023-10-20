package com.shopping.server.graphql.security


/**
 * Marking annotation that will switch off security check for given method.
 * Works only for methods defined in GraphQL Resolvers
 */
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class Unsecured