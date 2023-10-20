package com.shopping.server.dao

import org.springframework.data.domain.Sort

class GraphQLSort(
    val orders: MutableList<GraphQLOrder> = ArrayList<GraphQLOrder>()
) {
    fun toSort(): Sort {
        return Sort.by(orders.map { it.toOrder() })
    }
}
