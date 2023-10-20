package com.shopping.server.dao

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

class GraphQLPage {
    var page: Int
    var size: Int
    var sort: GraphQLSort

    companion object {
        const val DEFAULT_SIZE = 20
        const val DEFAULT_PAGE = 0
    }

    constructor() {
        this.page = DEFAULT_PAGE
        this.size = DEFAULT_SIZE
        this.sort = GraphQLSort(ArrayList())
        print("1 constructor called")
    }

    constructor(page: Int, size: Int) {
        this.page = page
        this.size = size
        this.sort = GraphQLSort(ArrayList())
        print("2 constructor called")
    }

    constructor(page: Int, size: Int, sort: GraphQLSort) {
        this.page = page
        this.size = size
        this.sort = sort
        print("3 constructor called")
    }

    fun toPageable(): Pageable {
        return PageRequest.of(page, size, sort.toSort())
    }
}
