package com.example.repository

import com.example.constants.*
import com.example.models.ApiResponse
import com.example.models.Hero
import io.ktor.util.*

class HeroRepositoryImpl : HeroRepository {
    override val heroes: Map<Int, List<Hero>>
        get() = mapOf(
            1 to page1,
            2 to page2,
            3 to page3,
            4 to page4,
            5 to page5
        )
    override val page1: List<Hero>
        get() = Hero1
    override val page2: List<Hero>
        get() = Hero2
    override val page3: List<Hero>
        get() = Hero3
    override val page4: List<Hero>
        get() = Hero4
    override val page5: List<Hero>
        get() = Hero5

    override suspend fun getAllHeroes(page: Int): ApiResponse {
        return ApiResponse(
            success = true,
            message = "ok",
            prevPage = calculatePrevPage(page),
            nextPage = calculateNextPage(page),
            heroes = heroes[page]!!
        )
    }

    override suspend fun searchHeroes(name: String?): ApiResponse {
        return ApiResponse(
            success = true,
            message = "ok",
            heroes = findHeroes(name)
        )
    }

    private fun findHeroes(query: String?): List<Hero> {
        return if (query.isNullOrEmpty()) {
            emptyList()
        } else {
            val founded = mutableListOf<Hero>()
            heroes.forEach { (_, heroes) ->
                heroes.forEach { hero ->
                    if (hero.name.toLowerCasePreservingASCIIRules().contains(query.toLowerCasePreservingASCIIRules())) {
                        founded.add(hero)
                    }
                }
            }
            founded
        }
    }

    private fun calculatePrevPage(page: Int): Int? {
        if (page in 2..5) {
            return page.minus(1)
        }
        return null
    }

    private fun calculateNextPage(page: Int): Int? {
        if (page in 1..4) {
            return page.plus(1)
        }
        return null
    }
}