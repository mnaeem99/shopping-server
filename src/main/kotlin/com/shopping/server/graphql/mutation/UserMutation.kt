package com.shopping.server.graphql.mutation

import com.shopping.server.dao.*
import com.shopping.server.service.user.UserService
import graphql.kickstart.tools.GraphQLMutationResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import java.util.*

@Component
@Validated
class UserMutation @Autowired constructor(
    val userService: UserService,
): GraphQLMutationResolver {
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    fun inviteUser(args: InviteUserArgs): Any {
        return userService.inviteUser(args)
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    fun deleteUserById(args: DeleteUserByIdArgs): Any {
        return userService.deleteUserById(args)
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    fun changeUserPassword(args: ChangeUserPasswordArgs): Any {
        return userService.changeUserPassword(args)
    }

}