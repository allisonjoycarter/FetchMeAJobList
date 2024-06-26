package com.example.fetchmeajoblist

import com.example.fetchmeajoblist.data.HiringListRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class HiringListRepositoryTests {

    /**
     * Maybe one day this little function could grow into a test rule.
     */
    private fun setupRepositoryWithResponse(response: String): HiringListRepository {
        val mockEngine = MockEngine {
            respond(
                content = response,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json()
            }
        }

        return HiringListRepository(httpClient)
    }

    @Test
    fun `fetchHiringList returns items successfully`() = runTest {
        val hiringListRepository = setupRepositoryWithResponse(
            """
                [
                    {
                        "id": 1,
                        "listId": 1,
                        "name": "Item One on List One"
                    },
                    {
                        "id": 2,
                        "listId": 1,
                        "name": "Item Two on List One"
                    }
                ]
            """.trimIndent()
        )
        val hiringListItems = hiringListRepository.fetchHiringList()

        assertEquals(2, hiringListItems.size)
        assertEquals(1, hiringListItems.distinctBy { it.listId }.size)
    }

    @Test
    fun `fetchHiringList ignores null or blank names`() = runTest {
        val hiringListRepository = setupRepositoryWithResponse(
            """
                [
                    {
                        "id": 1,
                        "listId": 1,
                        "name": null
                    },
                    {
                        "id": 2,
                        "listId": 1,
                        "name": ""
                    }
                ]
            """.trimIndent()
        )
        val hiringListItems = hiringListRepository.fetchHiringList()

        assert(hiringListItems.isEmpty())
    }

    @Test
    fun `fetchHiringList sorts lists by listId then by name`() = runTest {
        val hiringListRepository = setupRepositoryWithResponse(
            """
                [
                    {
                        "id": 2,
                        "listId": 2,
                        "name": "DEF"
                    },
                    {
                        "id": 1,
                        "listId": 2,
                        "name": "ABC"
                    },
                    {
                        "id": 4,
                        "listId": 1,
                        "name": "ABC"
                    },
                    {
                        "id": 5,
                        "listId": 3,
                        "name": "DEF"
                    },
                    {
                        "id": 6,
                        "listId": 3,
                        "name": "ABC"
                    },
                    {
                        "id": 3,
                        "listId": 2,
                        "name": "JKL"
                    }
                ]
            """.trimIndent()
        )
        val hiringListItems = hiringListRepository.fetchHiringList()

        assertEquals(6, hiringListItems.size)
        assertEquals(3, hiringListItems.distinctBy { it.listId }.size)
        assertEquals(1, hiringListItems.first().listId)
        assertEquals("ABC", hiringListItems.first().name)
        assertEquals("ABC", hiringListItems.first { it.listId == 1 }.name)
        assertEquals("ABC", hiringListItems.first { it.listId == 2 }.name)
        assertEquals("JKL", hiringListItems.last { it.listId == 2 }.name)
    }
}