package com.example.routes

import com.example.models.ApiResponse
import com.example.repository.HeroRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.heroesRoute() {
    val heroRepository: HeroRepository by inject()

    get("/boruto/heroes") {
        try {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            require(page in 1..5)
            val response = heroRepository.getAllHeroes(page)
            call.respond(message = response, status = HttpStatusCode.OK)
        } catch (e: IllegalArgumentException) {
            call.respond(
                message = ApiResponse(success = false, message = "Heroes not found."),
                status = HttpStatusCode.BadRequest
            )
        }
    }
}