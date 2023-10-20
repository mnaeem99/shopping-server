package com.shopping.server.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.shopping.server.data.model.RefreshToken
import com.shopping.server.data.model.User
import com.shopping.server.data.repository.RefreshTokenRepository
import org.springframework.stereotype.Component
import java.security.SecureRandom
import java.util.*

@Component
class SecurityUtils(
    private val refreshTokenRepository: RefreshTokenRepository
) {

    fun generateAccessToken(user: User): String {
        val accessToken = RefreshToken()
        accessToken.user = user
        accessToken.token = this.token()
        refreshTokenRepository.save(accessToken)
        return this.jwtAccess(user, accessToken.token)
    }
    fun generateRefreshToken(user: User): String {
        val refreshToken = RefreshToken()
        refreshToken.user = user
        refreshToken.token = this.token()
        refreshTokenRepository.save(refreshToken)
        return this.jwtRefresh(user, refreshToken.token)
    }

    /**
     * Generate a JWT token for the specified user.
     * @param user User to generate the token for.
     * @return Either the JWT token string, or null if one couldn't be generated.
     */
    private fun jwtAccess(user: User, accessToken: String?): String {
        return JWT.create()
            .withSubject(user.id.toString())
            .withClaim("id", user.id.toString())
            .withClaim("accessToken", accessToken)
            .withClaim("role", user.userRole.toString())
            .withExpiresAt(
                Date(System.currentTimeMillis() + SecurityConstants.ACCESS_TOKEN_EXPIRATION)
            )
            .sign(Algorithm.HMAC512(SecurityConstants.SECRET_ACCESS.toByteArray()))
    }

    private fun jwtRefresh(user: User, refreshToken: String?): String {
        return JWT.create()
            .withSubject(user.id.toString())
            .withClaim("id", user.id.toString())
            .withClaim("refreshToken", refreshToken)
            .withClaim("role", user.userRole.toString())
            .withExpiresAt(
                    Date(System.currentTimeMillis() + SecurityConstants.REFRESH_TOKEN_EXPIRATION)
            )
            .sign(Algorithm.HMAC512(SecurityConstants.SECRET_Ref.toByteArray()))
    }

    private fun token(): String {
        val random = SecureRandom()
        val bytes = ByteArray(128)
        random.nextBytes(bytes)
        val encoder: Base64.Encoder = Base64.getUrlEncoder().withoutPadding()
        return encoder.encodeToString(bytes)
    }


}