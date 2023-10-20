package com.shopping.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import java.time.LocalDateTime

@SpringBootApplication
@EnableAspectJAutoProxy
class ServerApplication

fun main(args: Array<String>) {
	runApplication<ServerApplication>(*args)
	println("Starting shopping server now: " + LocalDateTime.now())
}
