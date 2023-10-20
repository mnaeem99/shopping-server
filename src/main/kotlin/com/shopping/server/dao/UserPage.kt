package com.shopping.server.dao

import com.shopping.server.data.model.User

data class UserPage(
        val totalElements: Int,
        val totalPages: Int,
        val content: List<User>
)