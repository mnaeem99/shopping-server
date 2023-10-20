package com.shopping.server.dao

data class ChangeUserPasswordArgs(
        val data: ChangeUserPasswordData,
        val where: ChangeUserPasswordWhere
)