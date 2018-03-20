package com.example

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.server.engine.*
import io.ktor.server.jetty.*

import java.io.File

class JsonResponse(val data: Any)
data class Model(val name: String, val items: List<Item>)
data class Item(val key: String, val value: String)

fun Application.module() {
  install(DefaultHeaders)
  install(CallLogging)
  install(Routing) {
    get("/") {
      call.respondText("Hello, World!")
    }
  }
}

fun main(args: Array<String>) {
  embeddedServer(Jetty, commandLineEnvironment(args)).start()
}
