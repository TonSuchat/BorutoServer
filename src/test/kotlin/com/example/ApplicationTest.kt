package com.example

import com.example.constants.Hero1
import com.example.models.ApiResponse
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun `access route endpoint`() = testApplication {
        client.get("/").apply {
            assertEquals(
                expected = HttpStatusCode.OK,
                actual = status
            )
            assertEquals(
                expected = "Welcome to Boruto API!",
                actual = bodyAsText().trim('"')
            )
        }
    }

    @Test
    fun `access all heroes endpoint`() = testApplication {
        client.get("/boruto/heroes").apply {
            val expectedApiResponse = ApiResponse(
                success = true,
                message = "ok",
                prevPage = null,
                nextPage = 2,
                heroes = Hero1
            )
            assertEquals(
                expected = HttpStatusCode.OK,
                actual = status
            )
            val actual = Json.decodeFromString<ApiResponse>(bodyAsText())
            assertEquals(
                expected = expectedApiResponse,
                actual = actual
            )
        }
    }

    @Test
    fun `access all heroes endpoint, query non existing page number`() = testApplication {
        client.get("/boruto/heroes?page=6").apply {
            val expectedApiResponse = ApiResponse(
                success = false,
                message = "Heroes not found."
            )
            assertEquals(
                expected = HttpStatusCode.BadRequest,
                actual = status
            )
            val actual = Json.decodeFromString<ApiResponse>(bodyAsText())
            assertEquals(
                expected = expectedApiResponse,
                actual = actual
            )
        }
    }

    @Test
    fun `access search heroes endpoint`() = testApplication {
        client.get("/boruto/heroes/search?name=sas").apply {
            assertEquals(
                expected = HttpStatusCode.OK,
                actual = status
            )
            val actual = Json.decodeFromString<ApiResponse>(bodyAsText()).heroes.size
            assertEquals(
                expected = 1,
                actual = actual
            )
        }
    }

    @Test
    fun `access search heroes endpoint, empty result`() = testApplication {
        client.get("/boruto/heroes/search?name=").apply {
            assertEquals(
                expected = HttpStatusCode.OK,
                actual = status
            )
            val actual = Json.decodeFromString<ApiResponse>(bodyAsText()).heroes
            assertEquals(
                expected = emptyList(),
                actual = actual
            )
        }
    }
}
