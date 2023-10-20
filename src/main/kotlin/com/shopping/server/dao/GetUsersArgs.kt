package com.shopping.server.dao

import com.shopping.server.data.model.Role

data class GetUsersArgs(
        val text: String?=null,
        val userRole: Role?=null,
)
