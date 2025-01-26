package com.example.demo.controller

import com.example.demo.service.AppService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException

@RestController
class AppController(private val appService: AppService) {

    @GetMapping("/print")
    fun print(): PrintResponse {
        val appInfo = appService.getAppInfo()
        return appInfo.toPrintResponse()
    }

    @CrossOrigin(origins = ["http://localhost:*"])
    @GetMapping("/sse")
    fun streamHelloWorld(): SseEmitter {
        val emitter = SseEmitter()

        Thread {
            try {
                repeat(10) {
                    emitter.send(SseEmitter.event().data("Hello ${System.currentTimeMillis()}"))
                    Thread.sleep(1000)
                }
                emitter.complete()
            } catch (e: IOException) {
                emitter.completeWithError(e)
            } catch (e: InterruptedException) {
                emitter.completeWithError(e)
            }
        }.start()

        return emitter
    }
}