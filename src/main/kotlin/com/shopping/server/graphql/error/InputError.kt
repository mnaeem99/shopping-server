package com.shopping.server.graphql.error

data class InputError(
    val message: String,
    val field: String,
)