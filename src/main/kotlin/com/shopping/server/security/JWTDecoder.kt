package com.shopping.server.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.shopping.server.data.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.stereotype.Component
import java.util.UUID


@Component
class JWTDecoder @Autowired constructor(
    private val userRepository: UserRepository,
) {
    val logger: Logger = LoggerFactory.getLogger(this.javaClass.name)

    fun getJWTAuthentication(tokenHeader: String?): UsernamePasswordAuthenticationToken? {
        if (tokenHeader != null) {
            // parse the token.
            val token = tokenHeader.replace(SecurityConstants.TOKEN_PREFIX, "")
            val userId: String
            val role: String?
            try {
                val jwtDecoder = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_ACCESS.toByteArray()))
                    .build()
                    .verify(token)
                userId = jwtDecoder.subject
                role = jwtDecoder.claims["role"]?.asString()
            }catch (e:Exception){
                e.stackTrace
                return null
            }
            val user = userRepository.findById(UUID.fromString(userId))
            if (user.isPresent) {
                val grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(role)
                return UsernamePasswordAuthenticationToken(user.get(), null, grantedAuthorities)
            }
        }
        return null
    }
    fun getRefreshTokenAuthentication(tokenHeader: String?): UsernamePasswordAuthenticationToken? {
        if (tokenHeader != null) {
            // parse the token.
            val token = tokenHeader.replace(SecurityConstants.TOKEN_PREFIX, "")
            val jwtDecoder = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_Ref.toByteArray()))
                    .build()
                    .verify(token)
            val userId = jwtDecoder.subject
            val role = jwtDecoder.claims["role"]?.asString()
            val user = userRepository.findById(UUID.fromString(userId))
            if (user.isPresent) {
                val grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(role)
                return UsernamePasswordAuthenticationToken(user.get(), null, grantedAuthorities)
            }
        }
        return null
    }

}