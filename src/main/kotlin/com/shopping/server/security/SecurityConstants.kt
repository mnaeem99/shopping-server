package com.shopping.server.security

import java.util.concurrent.TimeUnit

object SecurityConstants {
    const val SECRET_ACCESS = "SecretKeyToGenACCESS"
    const val SECRET_Ref = "SecretKeyToGenREF"
    val ACCESS_TOKEN_EXPIRATION = TimeUnit.MINUTES.toMillis(30)
    val REFRESH_TOKEN_EXPIRATION = TimeUnit.HOURS.toMillis(24)
    const val TOKEN_PREFIX = "Bearer "
    const val HEADER_STRING = "Authorization"
}
