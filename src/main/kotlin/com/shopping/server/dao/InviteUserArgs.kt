package com.shopping.server.dao

import com.shopping.server.data.model.Role

class InviteUserArgs (
        val fullName: String,
        val username: String,
        val password: String,
        val userRole: Role
)