package com.createfuture.takehome.ui.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.createfuture.takehome.R
import com.createfuture.data.ApiCharacter
import com.createfuture.takehome.ui.base.EditTextField

@Composable
fun ComposeHomeScreen(
    viewModel: ComposeHomeViewModel,
    characterList: List<ApiCharacter>?,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(
                    id = R.drawable.img_characters
                ), contentScale = ContentScale.FillBounds
            )
            .padding(10.dp)
    ) {
        EditTextField(
            label = stringResource(R.string.characters_title_search),
            text = "",
            onTextChange = { }
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (characterList != null)
            CharacterList(characterList)
    }

}

@Composable
fun CharacterList(characterList: List<ApiCharacter>) {
    LazyColumn {
        items(characterList) { characterDto ->
            CharacterRow(characterDto)
        }
    }
}

@Composable
fun CharacterRow(character: ApiCharacter) {
    Row(
        modifier = Modifier.padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier
                .weight(3f)
                .padding(end = 5.dp)
        ) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            PropertyRow(
                title = stringResource(R.string.characters_title_culture),
                text = character.culture
            )
            PropertyRow(
                title = stringResource(R.string.characters_title_born),
                text = character.born
            )
            PropertyRow(
                title = stringResource(R.string.characters_title_died),
                text = character.died
            )
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(R.string.characters_title_seasons),
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = character.tvSeries.toSeasonsString(),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(),
        color = Color.Gray
    )
}

@Composable
fun PropertyRow(title: String, text: String) {
    Row {
        Text(
            text = "$title ",
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

fun List<String>.toSeasonsString(): String {
    return this.joinToString {
        when (it) {
            "Season 1" -> "I"
            "Season 2" -> "II"
            "Season 3" -> "III"
            "Season 4" -> "IV"
            "Season 5" -> "V"
            "Season 6" -> "VI"
            "Season 7" -> "VII"
            "Season 8" -> "VIII"
            else -> ""
        }
    }
}