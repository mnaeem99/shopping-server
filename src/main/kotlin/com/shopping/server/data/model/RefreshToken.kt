package com.shopping.server.data.model

import jakarta.persistence.Entity
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.OffsetDateTime
import java.util.concurrent.TimeUnit

@Entity
data class RefreshToken (
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    var user: User? = null,

    @Column(unique = true, nullable = false)
    var token: String? = null,

    @CreationTimestamp
    var createdAt: OffsetDateTime? = null,

    @Column(nullable = false)
    var validityPeriod: Long = TimeUnit.DAYS.toMillis(1)
)