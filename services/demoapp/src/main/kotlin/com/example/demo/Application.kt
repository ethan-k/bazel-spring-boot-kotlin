package com.example.demo

import com.example.demo.service.AppService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication


@SpringBootApplication
@ConfigurationPropertiesScan
open class Application(private val appService: AppService): ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        appService.getAppInfo()
    }
}

fun main(args: Array<String>) {
    println("Hello world")
    runApplication<Application>(*args)
}