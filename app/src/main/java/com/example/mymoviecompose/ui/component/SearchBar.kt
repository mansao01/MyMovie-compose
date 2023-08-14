@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mymoviecompose.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.mymoviecompose.ui.screen.search.SearchViewModel

@Composable
fun MySearchBar(
    searchViewModel: SearchViewModel,
    modifier: Modifier = Modifier
) {
    var query by rememberSaveable {
        mutableStateOf("")
    }
    var isActive by remember {
        mutableStateOf(false)
    }

    SearchBar(
        query =query,
        onQueryChange ={query = it},
        onSearch ={
                  searchViewModel.searchMovie(query)
        },
        active =false,
        onActiveChange ={isActive =it},
        placeholder = { Text(text = "SearchMovie")},
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null)},
        trailingIcon = {
            if (isActive) {
                IconButton(
                    onClick = {
                        if (query.isNotEmpty()) {
                            query = ""
                        } else isActive = false
                    }
                ) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
                }
            }
        },
        content={},
        modifier = Modifier
    )

}