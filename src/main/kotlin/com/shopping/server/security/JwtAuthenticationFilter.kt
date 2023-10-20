package com.shopping.server.security


import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class JwtAuthenticationFilter @Autowired constructor(
        val jwtDecoder: JWTDecoder
): OncePerRequestFilter() {
    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain) {
        val header = req.getHeader(SecurityConstants.HEADER_STRING)
        if (req.servletPath.equals("/api/auth/access-token")){
            val authentication = jwtDecoder.getRefreshTokenAuthentication(
                    req.getHeader(SecurityConstants.HEADER_STRING)
            )
            SecurityContextHolder.getContext().authentication = authentication
            chain.doFilter(req, res)
        }


        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(req, res)
            return
        }

        //authenticate
        val authentication = jwtDecoder.getJWTAuthentication(header)
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(req, res)
    }
}