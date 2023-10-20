package com.shopping.server.service.auth

import com.shopping.server.dao.LoginDto

interface AuthenticationService {
    fun login(request: LoginDto?): HashMap<String, String>
}