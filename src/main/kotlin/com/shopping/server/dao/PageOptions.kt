package com.shopping.server.dao

class PageOptions(
        private val page: Int?=null,
        private val size: Int?=null,
        private val sort: GraphQLSort?=null
){
    fun toGraphQLPage(): GraphQLPage {
        val graphQLPage = GraphQLPage()
        if (page!=null){
            graphQLPage.page = page
        }
        if (size!=null){
            graphQLPage.size = size
        }
        if (sort!=null){
            graphQLPage.sort = sort
        }
        return graphQLPage
    }
}