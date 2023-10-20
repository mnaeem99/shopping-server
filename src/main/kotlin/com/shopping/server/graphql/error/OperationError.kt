package com.shopping.server.graphql.error

data class OperationError(
    override val message: String,
    val type: OperationErrorType = OperationErrorType.INTERNAL_ERROR,
) : RuntimeException(message)