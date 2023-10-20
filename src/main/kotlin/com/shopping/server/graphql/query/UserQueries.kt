package com.shopping.server.graphql.query

import com.shopping.server.dao.*
import com.shopping.server.data.repository.*
import com.shopping.server.service.user.UserService
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component

@Component
class UserQueries @Autowired constructor(
    val userService: UserService,
) : GraphQLQueryResolver{

    fun getMyself(): Any {
        return userService.getMyself()
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    fun getUsers(args: GetUsersArgs?, pageArgs: PageOptions?): UserPage {
        return userService.getUsers(args, pageArgs)
    }


}