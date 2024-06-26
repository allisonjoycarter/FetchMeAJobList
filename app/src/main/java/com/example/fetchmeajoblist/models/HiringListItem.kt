package com.example.fetchmeajoblist.models

import kotlinx.serialization.Serializable

@Serializable
data class HiringListItem(
    val id: Int,
    val listId: Int,
    val name: String? = null
)
