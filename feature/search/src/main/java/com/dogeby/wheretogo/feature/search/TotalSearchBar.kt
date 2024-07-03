package com.dogeby.wheretogo.feature.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dogeby.wheretogo.core.ui.components.listitem.KeywordListItem
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TotalSearchBar(
    query: () -> String,
    recentQueries: () -> List<String>,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var active by remember {
        mutableStateOf(false)
    }
    SearchBar(
        query = query(),
        onQueryChange = onQueryChange,
        onSearch = {
            onSearch(it)
            active = false
        },
        active = active,
        onActiveChange = { active = it },
        modifier = modifier,
        leadingIcon = {
            IconButton(
                onClick = {
                    if (active) {
                        onQueryChange("")
                        active = false
                    } else {
                        onNavigateUp()
                    }
                },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                )
            }
        },
        trailingIcon = {
            if (active) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null,
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                )
            }
        },
    ) {
        LazyColumn {
            items(recentQueries()) {
                KeywordListItem(
                    icon = Icons.Default.History,
                    keyword = it,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(),
                    ) {
                        onQueryChange(it)
                        onSearch(it)
                        active = false
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TotalSearchBarPreview() {
    var query by remember {
        mutableStateOf("")
    }
    TotalSearchBar(
        query = { query },
        recentQueries = {
            List(50) { "Search keyword $it" }.filter {
                it
                    .lowercase(Locale.getDefault())
                    .startsWith(query.lowercase(Locale.getDefault()))
            }
        },
        onQueryChange = {
            query = it
        },
        onSearch = {},
        onNavigateUp = {},
    )
}
