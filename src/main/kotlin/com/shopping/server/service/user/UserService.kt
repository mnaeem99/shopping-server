package com.shopping.server.service.user

import com.shopping.server.dao.*

interface UserService {
    fun getMyself(): Any
    fun getUsers(args: GetUsersArgs?, pageArgs: PageOptions?): UserPage
    fun inviteUser(args: InviteUserArgs): Any
    fun deleteUserById(args: DeleteUserByIdArgs): Any
    fun changeUserPassword(args: ChangeUserPasswordArgs): Any
}