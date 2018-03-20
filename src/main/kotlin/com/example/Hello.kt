package com.example

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.server.engine.*
import io.ktor.server.jetty.*
import io.ktor.gson.*

import java.io.File
import java.text.*

class JsonResponse(val data: Any)
data class Model(val name: String, val items: List<Item>)
data class Item(val key: String, val value: String)

fun Application.module() {
  install(DefaultHeaders)
  install(CallLogging)
  install(ContentNegotiation) {
    gson {
      setDateFormat(DateFormat.LONG)
      setPrettyPrinting()
    }
  }
  val model = Model("root", listOf(Item("A", "Apache"), Item("B", "Bing")))
  install(Routing) {
    get("/hello") {
      call.respondText("Hello, World!")
    }
    get("/bye") {
      call.respondText("Bye")
    }
    get("/v1") {
      call.respond(model)
    }
    get("/v1/item/{key}") {
      val item = model.items.firstOrNull { it.key == call.parameters["key"] }
      if (item == null)
        call.respond(HttpStatusCode.NotFound)
      else
        call.respond(item)
    }
  }
}

fun main(args: Array<String>) {
  embeddedServer(Jetty, commandLineEnvironment(args)).start()
}
