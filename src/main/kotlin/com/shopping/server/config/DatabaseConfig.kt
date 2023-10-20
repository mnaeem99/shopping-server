package com.shopping.server.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource


@Configuration
class DatabaseConfig @Autowired constructor(
    @Value("\${db.host}")
    private var host:String,
    @Value("\${db.port}")
    private var port:String,
    @Value("\${db.dbname}")
    private var dbname:String,
    @Value("\${db.username}")
    private var username:String,
    @Value("\${db.password}")
    private var password:String,
) {

    @Bean
    fun dataSource(): DataSource? {
            return DataSourceBuilder
                .create()
                .url("jdbc:postgresql://$host:$port/$dbname")
                .username(username)
                .password(password)
                .build()
    }
}