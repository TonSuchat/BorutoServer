package com.example.plugins

import com.example.routes.heroesRoute
import com.example.routes.rootRoute
import com.example.routes.searchHeroRoute
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {
        static(remotePath = "/images") {
            resources("images")
        }
        rootRoute()
        heroesRoute()
        searchHeroRoute()
    }
}
