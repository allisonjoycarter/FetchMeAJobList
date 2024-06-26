package com.example.fetchmeajoblist.data

import com.example.fetchmeajoblist.models.HiringListItem
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class HiringListRepository @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun fetchHiringList(): List<HiringListItem> {
        val response = httpClient.get("https://fetch-hiring.s3.amazonaws.com/hiring.json")

        return response.body<List<HiringListItem>>()
            .filterNot { it.name.isNullOrBlank() } // remove blanks and null names
            .sortedWith(compareBy({ it.listId }, { it.name })) // sort by listId, then by name
    }
}