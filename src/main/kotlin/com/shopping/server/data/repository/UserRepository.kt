package com.shopping.server.data.repository

import com.shopping.server.data.model.Role
import com.shopping.server.data.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface UserRepository : JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    fun findByUsername(username: String?): Optional<User>
    @Query(
            "SELECT u FROM User u " +
                "WHERE ( :userRole IS NULL OR u.userRole = :userRole ) " +
                    "AND ( :keyword IS NULL OR " +
                        "LOWER(u.fullName) LIKE CONCAT('%', LOWER(cast(:keyword AS text)), '%') OR " +
                        "LOWER(u.username) LIKE CONCAT('%', LOWER(cast(:keyword AS text)), '%') " +
                    ") "
    )
    fun findAll(userRole: Role?, keyword: String?, pageable: Pageable): Page<User>
}