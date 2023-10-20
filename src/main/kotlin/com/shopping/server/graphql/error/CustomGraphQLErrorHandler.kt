package com.shopping.server.graphql.error

import graphql.ExceptionWhileDataFetching
import graphql.GraphQLError
import graphql.GraphqlErrorBuilder
import graphql.kickstart.execution.error.GraphQLErrorHandler
import org.springframework.stereotype.Component
import java.util.stream.Collectors


/**
 * Overriding default GraphQL implementation for exceptions processing
 */
@Component
class CustomGraphQLErrorHandler : GraphQLErrorHandler {

    override fun processErrors(errors: List<GraphQLError>): List<GraphQLError> {
        val clientErrors = errors.stream()
            .filter { error: GraphQLError? -> isClientError(error) }
            .collect(Collectors.toList())
        val serverErrors = errors.stream()
            .filter { e: GraphQLError? -> !isClientError(e) }
            .map { error: GraphQLError? ->
                toGraphQLError(
                    error!!
                )
            }
            .collect(Collectors.toList())
        val e: MutableList<GraphQLError> = ArrayList()
        e.addAll(clientErrors)
        e.addAll(serverErrors)
        return e
    }

    protected fun isClientError(error: GraphQLError?): Boolean {
        return !(error is ExceptionWhileDataFetching || error is Throwable)
    }
    fun toGraphQLError(error: GraphQLError): GraphQLError {
        if (error is ExceptionWhileDataFetching){
            if (error.exception is GraphQLError) {
                return GraphqlErrorBuilder.newError()
                    .errorType((error.exception as GraphQLError).errorType)
                    .message(error.exception.message)
                    .path(error.path)
                    .extensions(error.extensions)
                    .locations(error.locations)
                    .build()
            }
        }
        return error
    }
}