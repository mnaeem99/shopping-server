package com.shopping.server.graphql

import com.shopping.server.dao.*
import com.shopping.server.data.model.*
import com.shopping.server.graphql.error.InputValidationError
import com.shopping.server.graphql.error.OperationError
import com.shopping.server.graphql.scalar.CustomScalars
import graphql.kickstart.tools.SchemaParserDictionary
import graphql.schema.GraphQLScalarType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class GraphQLConfig {
    @Bean
    fun schemaParserDictionary(): SchemaParserDictionary? {
        return SchemaParserDictionary()
            .add(User::class.java)
            .add(OperationError::class.java)
            .add(InputValidationError::class.java)
            .add(UserPage::class.java)
            .add("PageOptions",GraphQLPage::class.java)
            .add("Sort",GraphQLSort::class.java)
            .add("Order",GraphQLOrder::class.java)
    }
    @Bean
    fun fileUpload(): GraphQLScalarType? {
        return CustomScalars.FileUpload
    }
    @Bean
    fun localDateTime(): GraphQLScalarType? {
        return CustomScalars.localDateTime
    }
}
