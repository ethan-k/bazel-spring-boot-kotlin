package com.example.demo.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "app")
data class AppConfig @ConstructorBinding constructor(
    val name: String,
    val description: String
)