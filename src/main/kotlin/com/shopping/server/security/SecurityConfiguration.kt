package com.shopping.server.security


import com.shopping.server.commons.Constants
import com.shopping.server.data.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.springframework.web.servlet.handler.HandlerMappingIntrospector


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfiguration @Autowired constructor(
        private val jwtAuthenticationFilter: JwtAuthenticationFilter,
        private val userRepository: UserRepository,
        @Value("#{'\${cors.allowedOrigins}'.split(',')}")
        private val allowedOrigins: List<String> = emptyList()
) {
    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() }
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests{ request ->
                    request.requestMatchers(antMatcher(HttpMethod.POST,"/api/auth/login")).permitAll()
                           .requestMatchers(antMatcher(HttpMethod.POST, "/api/graphql")).permitAll()
                           .requestMatchers(antMatcher(HttpMethod.GET, "/api/healthcheck")).permitAll()
                           .requestMatchers(antMatcher(HttpMethod.GET,"/api/download/*")).permitAll()
                           .anyRequest().authenticated()
                }
                .sessionManagement { manager: SessionManagementConfigurer<HttpSecurity?> -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowedOrigins = allowedOrigins
        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        config.allowedHeaders = listOf("*")
        source.registerCorsConfiguration("/**", config)
        source.registerCorsConfiguration("/*", config)
        return CorsFilter(source)
    }


    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun userDetailsService(): UserDetailsService? {
        return UserDetailsService { username: String? ->
            userRepository.findByUsername(username)
                    .orElseThrow { UsernameNotFoundException(Constants.UN_AUTHORIZED) }
        }
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider? {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService())
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager? {
        return config.authenticationManager
    }
}