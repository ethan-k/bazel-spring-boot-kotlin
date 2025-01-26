package com.example.demo.service

import com.example.demo.config.AppConfig
import org.springframework.stereotype.Component

@Component
class AppService(private val appConfig: AppConfig) {

    fun getAppInfo(): AppInfo {
        return AppInfo(
            appName = appConfig.name,
            description = appConfig.description
        )
    }
}