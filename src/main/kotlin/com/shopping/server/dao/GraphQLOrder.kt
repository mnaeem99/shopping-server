package com.shopping.server.dao

import org.springframework.data.domain.Sort

class GraphQLOrder(
    val property: String,
    val direction: Sort.Direction = Sort.Direction.ASC,
    val ignoreCase: Boolean = false,
    val nullHandling: Sort.NullHandling = Sort.NullHandling.NATIVE
) {
    fun toOrder(): Sort.Order {
        return if (ignoreCase) {
            Sort.Order(direction, property, nullHandling).ignoreCase()
        } else {
            Sort.Order(direction, property, nullHandling)
        }
    }
}
