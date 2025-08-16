package com.example.qparty.model

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: Int,
    val text: String
)