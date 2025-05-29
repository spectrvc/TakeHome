package com.createfuture.data

import com.createfuture.domain.CharacterDto

data class ApiCharacter(
    val name: String,
    val gender: String,
    val culture: String,
    val born: String,
    val died: String,
    val aliases: List<String>,
    val tvSeries: List<String>,
    val playedBy: List<String>,
)

fun ApiCharacter.toCharacterDto(): CharacterDto {
    return CharacterDto(
        name = this.name,
        culture = this.culture,
        born = this.born,
        died = this.died,
        tvSeries = this.tvSeries,
    )
}