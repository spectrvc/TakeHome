package com.createfuture.takehome.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(FlowPreview::class)
@Composable
fun EditTextField(
    label: String = "",
    text: String = "",
    onTextChange: (String) -> Unit = {},
) {
    var localText by remember { mutableStateOf(text) }
    var firstTime = true
    LaunchedEffect(key1 = text) {
        localText = text
        snapshotFlow { localText }
            .debounce(20L)
            .distinctUntilChanged()
            .onEach {
                if (firstTime)
                    firstTime = false
                else
                    onTextChange(localText)
            }
            .launchIn(this)
    }

    BasicTextField(
        value = localText,
        onValueChange = {
            localText = it
        },
        textStyle = MaterialTheme.typography.titleSmall,
        cursorBrush = SolidColor(Color.White),
        singleLine = true,
        decorationBox = { innerTextField ->
            CustomDecorationBox(
                localText = localText,
                innerTextField = innerTextField,
                label = label,
            )
        }
    )

}

@Composable
fun CustomDecorationBox(
    label: String = "",
    localText: String,
    innerTextField: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White.copy(alpha = 0.2f))
            .padding(10.dp)
    ) {
        if (localText.isEmpty())
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall
            )
        innerTextField()
    }
}



