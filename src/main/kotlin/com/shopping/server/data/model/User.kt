package com.shopping.server.data.model

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(name = "users")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        val id: UUID? = null,
        var fullName: String? = null,
        private var username: String? = null,
        private var password: String? = null,
        @Enumerated(EnumType.STRING)
        var userRole: Role? = null,
        var createdAt: LocalDateTime,
        var updatedAt: LocalDateTime,

        @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user")
        private var tokens: List<RefreshToken?>? = null

): UserDetails{
    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return listOf(SimpleGrantedAuthority(userRole?.name))
    }

    override fun getPassword(): String? {
        return password
    }

    override fun getUsername(): String? {
        return username
    }
    fun setPassword(password: String?) {
        this.password = password
    }

    fun setUsername(username: String?) {
        this.username = username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}