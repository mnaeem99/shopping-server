package com.shopping.server.service.auth

import com.shopping.server.dao.LoginDto
import com.shopping.server.data.model.User
import com.shopping.server.security.SecurityConstants
import com.shopping.server.security.SecurityUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service


@Service
class AuthenticationServiceImpl @Autowired constructor(
        val authenticationManager: AuthenticationManager,
        val securityUtils: SecurityUtils,
): AuthenticationService {

    override fun login(request: LoginDto?): HashMap<String, String> {
        val auth = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request?.username, request?.password))
        val user = auth.principal as User
        val accessToken = securityUtils.generateAccessToken(user)
        val refToken = securityUtils.generateRefreshToken(user)
        val tokens: HashMap<String,String> = HashMap<String,String>()
        tokens["accessToken"] = SecurityConstants.TOKEN_PREFIX + accessToken
        tokens["refreshToken"] = SecurityConstants.TOKEN_PREFIX + refToken
        return tokens
    }
}