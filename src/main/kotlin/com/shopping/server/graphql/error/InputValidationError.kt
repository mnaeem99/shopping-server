package com.shopping.server.graphql.error
data class InputValidationError(
    val errors: List<InputError>
)