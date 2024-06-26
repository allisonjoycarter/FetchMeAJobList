package com.example.fetchmeajoblist.ui.hiringList

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fetchmeajoblist.models.HiringListItem
import com.example.fetchmeajoblist.ui.theme.FetchMeAJobListTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HiringList(
    hiringItems: List<HiringListItem>,
    modifier: Modifier = Modifier
) {
    var collapsedGroups by remember { mutableStateOf(emptyList<Int>()) }

    LazyColumn(
        modifier = modifier
    ) {
        hiringItems.groupBy { it.listId }.forEach { (listId, listItems) ->
            val collapsed = collapsedGroups.contains(listId)

            stickyHeader {
                ListHeader(
                    listId = listId,
                    collapsed = collapsed,
                    onCollapse = { shouldCollapse ->
                        collapsedGroups = if (shouldCollapse) {
                            collapsedGroups.plus(listId)
                        } else {
                            collapsedGroups.filter { it != listId }
                        }
                    }
                )
            }

            if (!collapsed) {
                items(listItems) { item ->
                    ListItem(
                        headlineContent = { Text(item.name.orEmpty()) },
                        supportingContent = {
                            Text(
                                item.id.toString(),
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = .6f)
                            )
                        }
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HiringListPreview() {
    FetchMeAJobListTheme {
        HiringList(
            listOf(
                HiringListItem(
                    id = 1,
                    listId = 1,
                    name = "Dog Walker"
                ),
                HiringListItem(
                    id = 2,
                    listId = 1,
                    name = "Plumber"
                ),
                HiringListItem(
                    id = 3,
                    listId = 1,
                    name = "Sandwich Artist"
                ),
                HiringListItem(
                    id = 4,
                    listId = 2,
                    name = "Podcaster"
                ),
            )
        )
    }
}
