package com.shopping.server.mvc

import com.shopping.server.data.model.User
import com.shopping.server.security.SecurityConstants
import com.shopping.server.security.SecurityUtils
import com.shopping.server.service.auth.AuthenticationService
import com.shopping.server.dao.LoginDto
import com.shopping.server.graphql.error.OperationError
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api")
class AuthenticationController @Autowired constructor(
        private val authenticationService: AuthenticationService,
        private val securityUtils: SecurityUtils
){
    @PostMapping("/auth/login")
    fun login(@RequestBody request: LoginDto?): ResponseEntity<HashMap<String, String>> {
        return ResponseEntity.ok(authenticationService.login(request))
    }
    @GetMapping("/healthcheck")
    fun healthCheck(): ResponseEntity<String>? {
        return ResponseEntity.ok("ok")
    }


}