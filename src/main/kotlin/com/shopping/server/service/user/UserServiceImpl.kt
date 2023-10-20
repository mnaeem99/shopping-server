package com.shopping.server.service.user

import com.shopping.server.commons.Constants
import com.shopping.server.dao.*
import com.shopping.server.data.model.Role
import com.shopping.server.data.model.User
import com.shopping.server.data.repository.UserRepository
import com.shopping.server.graphql.error.OperationError
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {
    override fun getMyself(): Any {
        val principal = SecurityContextHolder.getContext().authentication.principal
        if (principal is User) {
            return principal
        }
        return OperationError(Constants.UN_AUTHORIZED)
    }

    override fun getUsers(args: GetUsersArgs?, pageArgs: PageOptions?): UserPage {
        val page = userRepository.findAll(args?.userRole, args?.text, pageArgs?.toGraphQLPage()?.toPageable() ?: GraphQLPage().toPageable())
        return UserPage(page.totalElements.toInt(), page.totalPages, page.content)
    }
    override fun inviteUser(args: InviteUserArgs): Any {
        if (args.userRole != Role.ADMIN) {
            return OperationError(Constants.UN_AUTHORIZED)
        }
        val foundUser = userRepository.findByUsername(args.username)
        if (foundUser.isPresent){
            return OperationError("This username is already in use.")
        }
        val user = User(fullName = args.fullName, username = args.username, password = passwordEncoder.encode(args.password), userRole = args.userRole, createdAt = LocalDateTime.now(), updatedAt = LocalDateTime.now())
        return userRepository.save(user)
    }

    override fun deleteUserById(args: DeleteUserByIdArgs): Any {
        val foundUser = userRepository.findByIdOrNull(args.id) ?: return OperationError(Constants.NOT_FOUND)
        userRepository.delete(foundUser)
        return foundUser
    }
    override fun changeUserPassword(args: ChangeUserPasswordArgs): Any {
        val foundUser = userRepository.findByIdOrNull(args.where.id) ?: return OperationError(Constants.NOT_FOUND)
        foundUser.password = passwordEncoder.encode(args.data.newPassword)
        return userRepository.save(foundUser)
    }
}