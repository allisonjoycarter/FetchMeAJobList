package com.example.fetchmeajoblist.ui.hiringList

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fetchmeajoblist.R
import com.example.fetchmeajoblist.ui.theme.FetchMeAJobListTheme

@Composable
fun ListHeader(
    listId: Int,
    onCollapse: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    collapsed: Boolean = false
) {
    val animateRotation by animateFloatAsState(
        targetValue = if (collapsed) 180f else 0f,
        animationSpec = tween(300, easing = LinearEasing)
    )

    Row(
        modifier = modifier
            .toggleable(collapsed, onValueChange = { onCollapse(it) })
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(R.string.list_x, listId),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Icon(
            Icons.Default.ArrowDropDown,
            if (collapsed) stringResource(R.string.expand_x, listId)
            else stringResource(R.string.collapse_x, listId),
            modifier = Modifier.rotate(animateRotation),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Preview
@Composable
fun ListHeaderPreview() {
    var open by remember { mutableStateOf(false) }

    FetchMeAJobListTheme {
        Column {
            ListHeader(
                listId = 12345,
                onCollapse = {
                    open = !open
                }
            )

            Text("Open = $open")
        }
    }
}
