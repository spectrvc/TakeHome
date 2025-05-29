package com.createfuture.domain

data class CharacterDto(
    val name: String,
    val culture: String,
    val born: String,
    val died: String,
    val tvSeries: List<String>,
)