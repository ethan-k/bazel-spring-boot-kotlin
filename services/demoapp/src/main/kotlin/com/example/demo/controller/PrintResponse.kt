package com.example.demo.controller

import com.example.demo.service.AppInfo

data class PrintResponse(val appName: String, val description: String)


fun AppInfo.toPrintResponse(): PrintResponse {
    return PrintResponse(
        appName = this.appName,
        description = this.description,
    )
}